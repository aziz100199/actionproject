package com.unit.spinneractivity.roomdatabase.viewmodel

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.unit.spinneractivity.roomdatabase.fragments.LoginRegisterFragment
import com.unit.spinneractivity.roomdatabase.room.database.UserDataBase
import com.unit.spinneractivity.roomdatabase.room.entities.DataEntity
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity
import com.unit.spinneractivity.roomdatabase.room.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class RoomViewModel(application: Application) : AndroidViewModel(application) {

    private var fragmentMLD = MutableLiveData<Fragment>()
    var fragmentLD = liveData {
        emitSource(fragmentMLD)
    }

    private var mutableLiveDataForUserInfo = MutableLiveData<UserEntity>()
    var liveDataForUserInfo = liveData {
        emitSource(mutableLiveDataForUserInfo)
    }


    private val db = UserDataBase.get(application)
    val repository = UserRepository(db)

    fun loadFragment(fragment: Fragment) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.test()
            fragmentMLD.postValue(fragment)
        }
    }

    fun addInUserData(item1: String, item2: String, item3: String, uid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userEntity = DataEntity()
            userEntity.userid = uid
            userEntity.timeformate1 = item1
            userEntity.timeformate2 = item2
            userEntity.timeformate3 = item3
            Timber.d("inserted ${uid}")
        }
    }

    fun submitLoginData(username: String, userpassword: String) {

        //        repository.checkIfUserExist(username, userpassword)

    }

    fun registerUsers(username: String, userpassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userEntity = UserEntity(username = username, password = userpassword)
            val isInserted =repository.regiseterUser(userEntity)
            if (isInserted > 0) {
                fragmentMLD.postValue(LoginRegisterFragment())
                // TODO: notifiuser

            } else {

                // TODO: notifiyerror
            }
        }
    }

}