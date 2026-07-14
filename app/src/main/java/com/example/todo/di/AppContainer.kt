package com.example.todo.di

import com.example.todo.domain.model.Task
import com.example.todo.domain.repository.TaskRepository
import com.example.todo.ui.drawer.DrawerViewModel
import com.example.todo.ui.homescreen.HomeScreenViewModel
import com.example.todo.ui.taskscreen.TaskScreenViewModel

interface AppContainer {
    val taskRepository: TaskRepository

    fun createDrawerViewModel(): DrawerViewModel
    fun createHomeScreenViewModel(): HomeScreenViewModel
    fun createTaskScreenViewModel(task: Task? = null): TaskScreenViewModel
}