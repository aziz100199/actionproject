package com.unit.spinneractivity.roomdatabase.viewmodel

import android.app.Application
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
    private var loginuser: UserEntity? = null
    private val db = UserDataBase.get(application)
    val repository = UserRepository(db)

    private var userDataListMLD = MutableLiveData(listOf<DataEntity>())
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
            fragmentMLD.postValue(fragment)
        }
    }

    fun deletDataItem(item: DataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletItem(item)
            getUserData()

        }
    }

    fun submitLoginData(username: String, userpassword: String) {
        Timber.d("username $username & password $userpassword")
        viewModelScope.launch(Dispatchers.IO) {
            val checkUsers = repository.checkIfUserExist(username, userpassword)
            if (checkUsers == true) {
                loginuser = repository.getUser(username, userpassword)
                Timber.d("submit id ${loginuser?.uid}")
                loginuser?.let {
                    repository
                    it.islogin = true
                    repository.updateUsers(it)
                    getUserData()
                    fragmentMLD.postValue(LoginSuccessFragment())

                }
            } else {
                // TODO: pleaseregister
            }
        }

    }


    private fun getUserData() {
        loginuser?.uid?.let { uid ->
            val userData = repository.getUserData(uid)
            userData?.let {
                userDataListMLD.postValue(it)
            }
        }
    }

    fun checkUserProfile(view: View) {
        viewModelScope.launch(Dispatchers.IO) {
            loginuser?.uid?.let {
                val data = repository.getUserData(it)
                data?.let {
                    if (it.isEmpty()) {
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

            }

        }

    }

    fun registerUsers(username: String, userpassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userEntity = UserEntity(username = username, password = userpassword)
            val isInserted = repository.regiseterUser(userEntity)
            if (isInserted > 0) {
                fragmentMLD.postValue(LoginRegisterFragment())
            } else {

                // TODO: notifiyerror

            }
        }
    }

//    fun onDatePicked(year: Int, mont: Int, dayofmont: Int) {
//        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.YEAR, year)
//        calendar.set(Calendar.MONTH, mont)
//        calendar.set(Calendar.DAY_OF_MONTH, dayofmont)
//        dateFormate(calendar)
//    }


    fun profileData(username: String, useremail: String) {
        viewModelScope.launch {

            val dataEntity =
                DataEntity(
                    userid = loginuser?.uid,
                    username = username,
                    useremail = useremail
                )
            repository.insertData(dataEntity)
            getUserData()

        }
    }
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
            loginuser = repository.getUserLogin()
            loginuser?.islogin = false
            loginuser?.let { repository.updateUsers(it) }

        }

    }


}