package com.unit.spinneractivity.roomdatabase.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity

@Dao
interface RoomDao {

    @Query("SELECT EXISTS(SELECT * FROM USERENTITY WHERE username=:username AND password =:userpassword)")
    fun checkIfUserExit(username: String, userpassword: String): Boolean

    @Query("SELECT * FROM USERENTITY WHERE username=:username AND password =:userpassword")
    fun findUsers(username: String, userpassword: String): List<UserEntity>?

    @Insert
    fun inserUSers(userEntity: UserEntity): Long

    @Query("SELECT * FROM USERENTITY")
    fun testpurpose(): List<UserEntity>?

    @Update
    fun updateUser(userEntity: UserEntity)


}