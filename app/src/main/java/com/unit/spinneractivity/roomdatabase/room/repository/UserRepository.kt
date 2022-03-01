package com.unit.spinneractivity.roomdatabase.room.repository

import com.unit.spinneractivity.roomdatabase.room.database.UserDataBase
import com.unit.spinneractivity.roomdatabase.room.entities.DataEntity
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity

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

    fun updateUsers(userEntity: UserEntity) {
        db.userDao().updateUser(userEntity)
    }

    fun getUser(username: String, userpassword: String): UserEntity? {
        return db.userDao().findUsers(username, userpassword)?.get(0)

    }

    fun insertData(dataEntity: DataEntity) {
        db.dataDao().insertData(dataEntity)
    }

    fun getUserData(): List<DataEntity>? {
     return db.dataDao().getAllData()
    }


}