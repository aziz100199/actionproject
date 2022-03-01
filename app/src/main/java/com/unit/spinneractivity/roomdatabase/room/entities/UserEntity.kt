package com.unit.spinneractivity.roomdatabase.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(

    @ColumnInfo(name = "username")
    var username: String?=null,

    @ColumnInfo(name = "password")
    var password: String?=null,

    @ColumnInfo(name = "islogin")
    var islogin: Boolean? = false,

    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null,
)
