package com.example.todo.data.repository

import com.example.todo.data.dao.CategoryDao
import com.example.todo.data.dao.StageDao
import com.example.todo.data.dao.TaskDao
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.data.model.TaskWithDetails
import com.example.todo.data.model.dataBaseColumn
import com.example.todo.data.model.dataBaseKeyword
import com.example.todo.data.model.toTaskEntity
import com.example.todo.domain.model.Task
import com.example.todo.domain.repository.TaskRepository
import com.example.todo.ui.homescreen.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalTaskRepository(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao,
    private val stageDao: StageDao
) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> = taskDao
        .getTasks()
        .mapToTaskList()

    override fun getFilteredTasks(searchState: SearchState): Flow<List<Task>> = taskDao
        .getFilteredTasks(
            query = searchState.query.ifBlank { null },
            category = searchState.category?.name,
            stage = searchState.stage?.name,
            sortOption = searchState.sortOption.dataBaseColumn,
            sortOrder = searchState.sortOrder.dataBaseKeyword,
            searchField = searchState.searchField.dataBaseColumn
        )
        .mapToTaskList()

    override fun getTasksByCategory(category: Category): Flow<List<Task>> = taskDao
        .getTasksByCategory(category.name)
        .mapToTaskList()

    override fun getTasksByStage(stage: Stage): Flow<List<Task>> = taskDao
        .getTasksByStage(stage.name)
        .mapToTaskList()

    override fun getTask(id: Int): Task? = taskDao.getTask(id)?.toTask()

    override suspend fun insertTask(task: Task) = withContext(Dispatchers.IO) {
        taskDao.insertTask(
            task.toTaskEntity()
        ).toInt()
    }
    override suspend fun updateTask(task: Task) = withContext(Dispatchers.IO) {
        taskDao.updateTask(
            task.toTaskEntity()
        )
    }
    override suspend fun deleteTask(taskIds: Set<Int>) = taskDao.deleteTask(taskIds)

    override fun getCategories(): Flow<List<Category>> = categoryDao.getCategories()
    override suspend fun addCategory(category: Category) = withContext(Dispatchers.IO) {
        categoryDao.addCategory(
            category
        )
    }
    override suspend fun removeCategory(category: Category) = withContext(Dispatchers.IO) {
        categoryDao.removeCategory(
            category
        )
    }

    override fun getStages(): Flow<List<Stage>> = stageDao.getStages()
    override suspend fun addStage(stage: Stage) = withContext(Dispatchers.IO) {
        stageDao.addStage(
            stage
        )
    }
    override suspend fun removeStage(stage: Stage) = withContext(Dispatchers.IO) {
        stageDao.removeStage(
            stage
        )
    }

    private fun Flow<List<TaskWithDetails>>.mapToTaskList(): Flow<List<Task>> = map { taskWithDetails -> taskWithDetails.map { it.toTask() } }
}