package com.unit.spinneractivity.roomdatabase.room.dao

import androidx.room.*
import com.unit.spinneractivity.roomdatabase.room.entities.DataEntity

@Dao
interface DataDao {
    @Insert
    fun insertData(userEntity: DataEntity)

    @Query("SELECT * FROM DataEntity WHERE userid=:uid")
    fun getAllData(uid: Int): DataEntity?

    @Delete
    fun deletUserData(item: DataEntity)

    @Update
    fun updateUser(dataentity: DataEntity)

}