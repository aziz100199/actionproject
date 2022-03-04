package com.unit.spinneractivity.roomdatabase.room.repository

import com.unit.spinneractivity.roomdatabase.room.database.UserDataBase
import com.unit.spinneractivity.roomdatabase.room.entities.DataEntity
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity
import kotlinx.coroutines.*


class UserRepository(var db: UserDataBase) {
    suspend fun checkIfUserExist(username: String, userpassword: String): Boolean {
        return db.userDao().checkIfUserExit(username, userpassword)
    }

    suspend fun regiseterUser(userEntity: UserEntity): Long {
        return db.userDao().inserUSers(userEntity)

    }

    suspend fun test() {
        db.userDao().testpurpose()

    }

    suspend fun updateUsers(userEntity: UserEntity) {
        withContext(Dispatchers.IO){
            db.userDao().updateUser(userEntity)
        }

    }

    fun getUser(username: String, userpassword: String): UserEntity? {
        return db.userDao().findUsers(username, userpassword)?.get(0)

    }
    fun sigleuser(username: String, userpassword: String): UserEntity? {
        return db.userDao().checkSingleuser(username, userpassword)

    }
  suspend  fun getUserLogin(): UserEntity? {
      return  withContext(Dispatchers.IO) {
            db.userDao().userLogin()
        }

    }

  suspend fun insertData(dataEntity: DataEntity) {
      withContext(Dispatchers.IO){
        db.dataDao().insertData(dataEntity)
      }


    }

    suspend fun getUserData(uid: Int): DataEntity? {
        return withContext(Dispatchers.IO){
            db.dataDao().getAllData(uid)
        }
    }

    fun deletItem(item: DataEntity) {
        return db.dataDao().deletUserData(item)
    }

   suspend fun updateData(dataentity: DataEntity) {
        withContext(Dispatchers.IO){
            db.dataDao().updateUser(dataentity)
        }
    }


}