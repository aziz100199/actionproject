package com.unit.spinneractivity.roomdatabase.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DataEntity(
    @PrimaryKey(autoGenerate = true)
    val Id: Int? = null,
    var userid: Int? = null,
    @ColumnInfo(name = "timeformate")
    var timeformate1: String? = null,
    var timeformate2: String? = null,
    var timeformate3: String? = null,

    )