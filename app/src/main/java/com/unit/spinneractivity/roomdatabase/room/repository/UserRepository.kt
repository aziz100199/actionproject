package com.unit.spinneractivity.roomdatabase.room.repository

import com.unit.spinneractivity.roomdatabase.room.database.UserDataBase
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity

class UserRepository(var db: UserDataBase) {
    suspend fun checkIfUserExist(username: String, userpassword: String) {
        val allUsers = db.dao().getALLUsers(username, userpassword)

    }

    suspend fun regiseterUser(userEntity: UserEntity): Long {
        return db.dao().inserUSers(userEntity)

    }

   suspend fun test() {
         db.dao().testpurpose()

    }

}