package com.unit.spinneractivity.roomdatabase.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity

@Dao
interface RoomDao {

    @Query("SELECT * FROM USERENTITY WHERE username=:username AND password =:userpassword")
    fun getALLUsers(username: String, userpassword: String): UserEntity? = null

    @Insert
    fun inserUSers(userEntity: UserEntity): Long

    @Query("SELECT * FROM USERENTITY")
    fun testpurpose():List<UserEntity>?=null


}