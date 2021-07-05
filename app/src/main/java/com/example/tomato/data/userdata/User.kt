package com.example.tomato.data.userdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.tomato.data.taskdata.Task


@Entity(tableName = "user")

data class User(@PrimaryKey val id: String,
                @ColumnInfo val password: String)