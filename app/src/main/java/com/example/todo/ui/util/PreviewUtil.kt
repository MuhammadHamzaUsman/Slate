package com.example.todo.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.model.Task
import com.example.todo.domain.repository.TaskRepository
import com.example.todo.ui.homescreen.SearchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Preview
@Composable
fun previewTaskRepository(): TaskRepository = object : TaskRepository {
    override fun getTasks(): Flow<List<Task>> = flowOf()
    override fun getFilteredTasks(searchState: SearchState): Flow<List<Task>> = flowOf()
    override fun getTask(id: Int): Task? = null
    override suspend fun insertTask(task: Task): Int = 0
    override suspend fun updateTask(task: Task) {}
    override suspend fun deleteTask(taskIds: Set<Int>) {}
    override fun getCategories(): Flow<List<Category>> = flowOf( listOf(Category.DEFAULT_CATEGORY) )
    override suspend fun addCategory(category: Category) {}
    override suspend fun removeCategory(category: Category) {}
    override fun getStages(): Flow<List<Stage>> = flowOf( listOf(Stage.INCOMPLETE_STAGE, Stage.COMPLETED_STAGE) )
    override suspend fun addStage(stage: Stage) {}
    override suspend fun removeStage(stage: Stage) {}
}