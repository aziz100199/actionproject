package com.unit.spinneractivity.roomdatabase.room.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.unit.spinneractivity.roomdatabase.room.dao.RoomDao
import com.unit.spinneractivity.roomdatabase.room.entities.DataEntity
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity

@Database(version = 1, entities = [UserEntity::class, DataEntity::class])

abstract class UserDataBase : RoomDatabase() {
    abstract fun dao(): RoomDao

    companion object {

        var instance: UserDataBase? = null
        fun get(application: Application): UserDataBase {

            if (instance == null) {
                instance =
                    Room.databaseBuilder(application, UserDataBase::class.java, "UserDataBase")
                        .fallbackToDestructiveMigration().build()
            }
            return instance as UserDataBase
        }

    }


}