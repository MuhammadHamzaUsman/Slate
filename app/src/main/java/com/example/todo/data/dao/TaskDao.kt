package com.example.todo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.todo.data.model.TaskEntity
import com.example.todo.data.model.TaskWithDetails
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
    fun getTask(id: Int): TaskWithDetails?

    @Transaction
    @Query("""
        SELECT * FROM task
        WHERE (:query IS NULL OR 
            CASE WHEN :searchField = 'title' THEN title
                WHEN :searchField = 'descrption' THEN description END
            LIKE '%' || :query || '%') AND
            (:category IS NULL OR category = :category) AND
            (:stage IS NULL OR stage = :stage) 
        ORDER BY
            CASE WHEN :sortOrder = 'ASC' AND :sortOption = 'id' THEN id END ASC,
            CASE WHEN :sortOrder = 'DESC' AND :sortOption = 'id' THEN id END DESC,
            
            CASE WHEN :sortOrder = 'ASC' AND :sortOption = 'createdAt' THEN createdAt END ASC,
            CASE WHEN :sortOrder = 'DESC' AND :sortOption = 'createdAt' THEN createdAt END DESC,
            
            CASE WHEN :sortOrder = 'ASC' AND :sortOption = 'updatedAt' THEN updatedAt END ASC,
            CASE WHEN :sortOrder = 'DESC' AND :sortOption = 'updatedAt' THEN updatedAt END DESC
    """)
    fun getFilteredTasks(
        query: String?,
        category: String?,
        stage: String?,
        sortOption: String,
        sortOrder: String,
        searchField: String
    ): Flow<List<TaskWithDetails>>

    @Insert
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM task WHERE id IN (:taskIds)")
    suspend fun deleteTask(taskIds: Set<Int>)
}