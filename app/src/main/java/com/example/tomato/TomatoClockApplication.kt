package com.example.tomato

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.util.DBUtil
import com.example.tomato.data.taskdata.AppDatabase
import com.example.tomato.data.taskdata.TaskDao
import com.example.tomato.data.userdata.UserDao

class TomatoClockApplication : Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var tomatoClockApplicationContext: Context
        lateinit var db: AppDatabase
        lateinit var taskDao: TaskDao
        lateinit var userDao: UserDao
    }

    override fun onCreate() {
        super.onCreate()
        tomatoClockApplicationContext = applicationContext
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()
        taskDao = db.taskDao()
        userDao = db.userDao()
    }
}