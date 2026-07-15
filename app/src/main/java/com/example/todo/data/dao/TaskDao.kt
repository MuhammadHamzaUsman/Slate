package com.example.todo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.todo.data.model.TaskWithDetails
import com.example.todo.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Transaction
    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM task WHERE category = :category")
    fun getTasksByCategory(category: String): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM task WHERE stage = :stage")
    fun getTasksByStage(stage: String): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM task WHERE id = :id ")
    fun getTask(id: Int): Flow<TaskWithDetails?>

    @Transaction
    @Query("""
        SELECT * FROM task
        WHERE (:title IS NULL OR title LIKE '%' || :title || '%') AND
            (:category IS NULL OR category = :category) AND
            (:stage IS NULL OR stage = :stage) 
    """)
    fun getFilteredTasks(title: String?, category: String?, stage: String?): Flow<List<TaskWithDetails>>

    @Insert
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM task WHERE id IN (:taskIds)")
    suspend fun deleteTask(taskIds: Set<Int>)
}