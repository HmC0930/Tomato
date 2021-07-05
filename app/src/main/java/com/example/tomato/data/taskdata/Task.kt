package com.example.tomato.data.taskdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tomato.data.userdata.User

@Entity(tableName = "task")

data class Task(@PrimaryKey val name: String,
           @ColumnInfo(name = "type") val taskType: String,
           val workingTime: Int,
           val restTime: Int,
           @ColumnInfo val userCreatorID: String,
           val status: Int
)