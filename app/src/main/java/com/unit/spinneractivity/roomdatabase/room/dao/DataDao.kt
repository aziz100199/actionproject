package com.unit.spinneractivity.roomdatabase.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.unit.spinneractivity.roomdatabase.room.entities.DataEntity

@Dao
interface DataDao {
    @Insert
    fun insertData(userEntity: DataEntity)

    @Query("SELECT * FROM DataEntity WHERE userid=:uid")
    fun getAllData(uid: Int): List<DataEntity>?

}