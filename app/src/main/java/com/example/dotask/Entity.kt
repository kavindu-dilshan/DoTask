package com.example.dotask

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DoTask")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String,
    var priority:String,
    var description: String
)