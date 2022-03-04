package com.unit.spinneractivity.roomdatabase.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.unit.spinneractivity.R
import com.unit.spinneractivity.roomdatabase.fragments.LoginRegisterFragment
import com.unit.spinneractivity.roomdatabase.fragments.LoginSuccessFragment
import com.unit.spinneractivity.roomdatabase.fragments.ProfileFragment
import com.unit.spinneractivity.roomdatabase.room.database.UserDataBase
import com.unit.spinneractivity.roomdatabase.room.entities.DataEntity
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity
import com.unit.spinneractivity.roomdatabase.room.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class RoomViewModel(application: Application) : AndroidViewModel(application) {
    val sp: SharedPreferences =
        application.getSharedPreferences("logininf",
            Context.MODE_PRIVATE)

    private var loginuser: UserEntity? = null
    private val db = UserDataBase.get(application)
    val repository = UserRepository(db)

    private var userDataListMLD = MutableLiveData(DataEntity())
    var userDataListLD = liveData {
        emitSource(userDataListMLD)

    }


    private var fragmentMLD = MutableLiveData<Fragment>()
    var fragmentLD = liveData {
        emitSource(fragmentMLD)

    }


    fun loadFragment(fragment: Fragment) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.test()
            getUserData()
            fragmentMLD.postValue(fragment)

        }
    }

    fun load() {
        viewModelScope.launch {
            val name = sp.getString("username", null)
            val psd = sp.getString("userpassword", null)
            Timber.d("Loadfragment vlue $name")
            name?.let { logname ->
                psd?.let { logpsd ->
                    submitLoginData(logname, logpsd)
                }
            }


            if (loginuser?.islogin == true) {
                Timber.d("load1")
                loadFragment(LoginSuccessFragment())

            } else {
                Timber.d("load2")
                loadFragment(LoginRegisterFragment())
            }
        }
    }

    fun deletDataItem(item: DataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletItem(item)
            getUserData()


        }
    }

    fun submitLoginData(username: String, userpassword: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val editor: SharedPreferences.Editor = sp.edit()
            editor.putString("username", username)
            editor.putString("userpassword", userpassword)
            editor.apply()


            val checkUsers = repository.checkIfUserExist(username, userpassword)
            if (checkUsers == true) {
                loginuser = repository.getUser(username, userpassword)
                loginuser?.let {
                    it.islogin = true
                    repository.updateUsers(it)
                    getUserData()
                    fragmentMLD.postValue(LoginSuccessFragment())

                }
            } else {
//                snackBar(loginsnackbar, "incorrect email or pasword")
            }
        }
    }


    private fun getUserData() {
        viewModelScope.launch {
            Timber.d("in view model uid ${loginuser?.uid}")
            loginuser?.uid?.let { uid ->
                val userData = repository.getUserData(uid)
                userData?.let {
                    userDataListMLD.postValue(it)
                }
            }
        }

    }

    fun checkUserProfile(view: View) {
        viewModelScope.launch {

            Snackbar.make(view,
                "Please fill your informaton",
                Snackbar.LENGTH_INDEFINITE).setAction("Profile") {
                loadFragment(ProfileFragment())
            }.setActionTextColor(ContextCompat.getColor(getApplication(),
                R.color.green))
                .setBackgroundTint(ContextCompat.getColor(getApplication(),
                    R.color.black)).show()
        }
    }

    fun registerUsers(username: String, userpassword: String, registersnackbar: View) {
        viewModelScope.launch(Dispatchers.IO) {
            val getuser = repository.db.userDao().checkSingleuser(username, userpassword)
            if (getuser?.username == username) {
                snackBar(registersnackbar, "user already exit")
            } else {
                val userEntity = UserEntity(username = username, password = userpassword)
                val isInserted = repository.regiseterUser(userEntity)
                if (isInserted > 0) {
                    fragmentMLD.postValue(LoginRegisterFragment())

                } else {

                    // TODO: notifiyerror

                }
            }

        }
    }


    fun profileData(username: String, useremail: String, imagestring: String?) {
        viewModelScope.launch {
            Timber.d("user id ${loginuser?.uid}")
            val dataEntity =
                DataEntity(
                    userid = loginuser?.uid,
                    username = username,
                    useremail = useremail,
                    imageuri = imagestring
                )
            repository.insertData(dataEntity)
            getUserData()
            loadFragment(LoginSuccessFragment())
        }
    }

    //    fun onDatePicked(year: Int, mont: Int, dayofmont: Int) {
//        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.YEAR, year)
//        calendar.set(Calendar.MONTH, mont)
//        calendar.set(Calendar.DAY_OF_MONTH, dayofmont)
//        dateFormate(calendar)
//    }

//
//    fun dateFormate(calendar: Calendar) {
//
//        viewModelScope.launch{
//            val dateformate = SimpleDateFormat("dd_MM_yyyy", Locale.getDefault())
//            val dateconvert = dateformate.format(calendar.time)
//            val dateformate1 = SimpleDateFormat("MM_dd_yyyy", Locale.getDefault())
//            val dateconvert1 = dateformate1.format(calendar.time)
//            val dateformate2 = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault())
//            val dateconvert2 = dateformate2.format(calendar.time)
//
//            val dataEntity = DataEntity(
//                userid = loginuser?.uid,
//                timeformate1 = dateconvert,
//                timeformate2 = dateconvert1,
//                timeformate3 = dateconvert2
//            )
//            repository.insertData(dataEntity)
//            getUserData()
//        }
//
//
//    }

//    fun deletItemOnPositon(itemposition: Int) {
//        val getitemposition = userDataListMLD.value?.get(itemposition)
//        if (getitemposition != null) {
//            deletDataItem(getitemposition)
//        }
//    }


    fun logout() {
        viewModelScope.launch {

            loginuser?.islogin = false
            loginuser?.let {
                repository.updateUsers(it)
            }
            sp.edit().clear().apply()
            Timber.d("logout second ${loginuser?.islogin}")
        }

    }

    fun snackBar(view: View, text: String) {
        Snackbar.make(view,
            text,
            Snackbar.LENGTH_SHORT).show()
    }

    fun updateuser(usernameupdate: String, useremailupdate: String) {
        Timber.d("updateuserCall")
        viewModelScope.launch {
            Timber.d("username ${usernameupdate}")
            val dataentity = DataEntity(username = usernameupdate, useremail = useremailupdate)
            repository.updateData(dataentity)
            loadFragment(LoginSuccessFragment())
            getUserData()
        }

    }


}

