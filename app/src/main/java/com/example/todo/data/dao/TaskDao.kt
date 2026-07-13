package com.example.todo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.data.model.TaskWithDetails
import com.example.todo.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<TaskWithDetails>>

    @Query("SELECT * FROM task WHERE category = :category")
    fun getTasksByCategory(category: String): Flow<List<TaskWithDetails>>

    @Query("SELECT * FROM task WHERE stage = :stage")
    fun getTasksByStage(stage: String): Flow<List<TaskWithDetails>>

    @Query("SELECT * FROM task WHERE id = :id ")
    fun getTask(id: Int): Flow<TaskWithDetails>

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}