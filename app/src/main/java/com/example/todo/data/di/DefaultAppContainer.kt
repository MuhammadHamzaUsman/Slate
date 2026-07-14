package com.example.todo.data.di

import android.content.Context
import com.example.todo.data.ToDoDatabase
import com.example.todo.data.repository.LocalTaskRepository
import com.example.todo.data.repository.TaskRepository

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
}