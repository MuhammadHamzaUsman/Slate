package com.example.todo.data.repository

import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    fun getTasksByCategory(category: String): Flow<List<Task>>
    fun getTasksByStage(stage: String): Flow<List<Task>>
    fun getTask(id: Int): Flow<Task?>

    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)

    fun getCategories(): Flow<List<Category>>
    suspend fun addCategory(category: Category)
    suspend fun removeCategory(category: Category)

    fun getStages(): Flow<List<Stage>>
    suspend fun addStage(stage: Stage)
    suspend fun removeStage(stage: Stage)
}