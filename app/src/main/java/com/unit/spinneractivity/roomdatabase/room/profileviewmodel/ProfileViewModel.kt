package com.unit.spinneractivity.roomdatabase.room.profileviewmodel

import android.app.Application
import android.service.autofill.UserData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.unit.spinneractivity.roomdatabase.room.database.UserDataBase
import com.unit.spinneractivity.roomdatabase.room.entities.DataEntity
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity
import com.unit.spinneractivity.roomdatabase.room.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {


    val db = UserDataBase.get(application)
    val repository = UserRepository(db)
    var islogin: UserEntity? = null

    private val userdataMLD = MutableLiveData(listOf<DataEntity>())
    val userDataLD = liveData {
        emitSource(userdataMLD)
    }

    fun getUserData() {
        viewModelScope.launch {
            islogin = repository.getUserLogin()
            islogin?.uid?.let {id->
                val userData = repository.getUserData(id)
           userData?.let {
//               userdataMLD.postValue(it)
           }
            }

        }

    }


}