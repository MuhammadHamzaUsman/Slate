package com.example.todo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import com.example.todo.ToDoApplication
import com.example.todo.domain.model.Task
import com.example.todo.ui.drawer.DrawerViewModel
import com.example.todo.ui.homescreen.HomeScreenViewModel
import com.example.todo.ui.taskscreen.TaskScreenViewModel

object AppViewModelProvider {
    fun drawerViewModelFactory() = viewModelFactory{ container -> DrawerViewModel(container.taskRepository) }
    fun homeScreenViewModelFactory() = viewModelFactory{ container -> HomeScreenViewModel(container.taskRepository) }
    fun taskScreenViewModelFactory(task: Task?) = viewModelFactory{ container -> TaskScreenViewModel(task, container.taskRepository) }

    private inline fun <reified T: ViewModel> viewModelFactory(
        crossinline viewModelInitializer: (AppContainer) -> T
    ): ViewModelProvider.Factory  = androidx.lifecycle.viewmodel.viewModelFactory {
        initializer {
            val application = this[APPLICATION_KEY] as ToDoApplication
            val container = application.container
            viewModelInitializer(container)
        }
    }
}