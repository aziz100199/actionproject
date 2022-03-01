package com.unit.spinneractivity.roomdatabase.room.repository

import com.unit.spinneractivity.roomdatabase.room.database.UserDataBase
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity

class UserRepository(var db: UserDataBase) {
    suspend fun checkIfUserExist(username: String, userpassword: String): Boolean {
        return db.dao().checkIfUserExit(username, userpassword)
    }

    suspend fun regiseterUser(userEntity: UserEntity): Long {
        return db.dao().inserUSers(userEntity)

    }

    suspend fun test() {
        db.dao().testpurpose()

    }

    fun updateUsers(userEntity: UserEntity) {
        db.dao().updateUser(userEntity)
    }

    fun getUser(username: String, userpassword: String): UserEntity? {
        return db.dao().findUsers(username, userpassword)?.get(0)

    }


}