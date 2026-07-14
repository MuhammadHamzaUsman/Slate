package com.example.todo.domain.repository

import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.model.Task
import com.example.todo.ui.homescreen.SearchState
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    fun getFilteredTasks(searchState: SearchState): Flow<List<Task>>
    fun getTasksByCategory(category: Category): Flow<List<Task>>
    fun getTasksByStage(stage: Stage): Flow<List<Task>>
    fun getTask(id: Int): Flow<Task?>

    suspend fun insertTask(task: Task): Int
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskIds: Set<Int>)

    fun getCategories(): Flow<List<Category>>
    suspend fun addCategory(category: Category)
    suspend fun removeCategory(category: Category)

    fun getStages(): Flow<List<Stage>>
    suspend fun addStage(stage: Stage)
    suspend fun removeStage(stage: Stage)
}