package com.example.todo.di

import android.content.Context
import com.example.todo.data.ToDoDatabase
import com.example.todo.data.repository.LocalTaskRepository
import com.example.todo.domain.model.Task
import com.example.todo.domain.repository.TaskRepository
import com.example.todo.ui.drawer.DrawerViewModel
import com.example.todo.ui.homescreen.HomeScreenViewModel
import com.example.todo.ui.taskscreen.TaskScreenViewModel

class DefaultAppContainer(private val appContext: Context) : AppContainer{
    private val toDoDatabase by lazy {
        ToDoDatabase.getInstance(appContext)
    }

    override val taskRepository: TaskRepository by lazy {
        LocalTaskRepository(
            taskDao = toDoDatabase.getTaskDao(),
            categoryDao = toDoDatabase.getCategoryDao(),
            stageDao = toDoDatabase.getStageDao()
        )
    }

    override fun createDrawerViewModel() = DrawerViewModel(taskRepository)
    override fun createHomeScreenViewModel() = HomeScreenViewModel(taskRepository)
    override fun createTaskScreenViewModel(task: Task?) = TaskScreenViewModel(task, taskRepository)
}