package com.example.tomato.data.taskdata

import androidx.room.*


@Dao
interface TaskDao {

    @Query("select * from task")
    fun getAll(): List<Task>

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task WHERE type LIKE :type")
    fun queryByType(type: String):Task

    @Insert(entity = Task::class)
    fun insert(task: Task)

    @Update
    fun updateTask(task:Task)
}

