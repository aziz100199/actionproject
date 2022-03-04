package com.unit.spinneractivity.roomdatabase.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    val Id: Int? = null,
    var userid: Int? = null,
    @ColumnInfo(name = "userinfo")
    var username:String?=null,
    var useremail:String?=null,
    var imageuri:String?=null,
//    var timeformate1: String? = null,
//    var timeformate2: String? = null,
//    var timeformate3: String? = null,

    )