package com.example.todo.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import com.example.todo.ToDoApplication
import com.example.todo.ui.drawer.DrawerViewModel
import com.example.todo.ui.homescreen.HomeScreenViewModel
import com.example.todo.ui.taskscreen.TaskScreenViewModel

object AppViewModelProvider {
    fun drawerViewModelFactory() = viewModelFactory{ container, _ -> DrawerViewModel(container.taskRepository) }
    fun homeScreenViewModelFactory() = viewModelFactory{ container, _ -> HomeScreenViewModel(container.taskRepository) }
    fun taskScreenViewModelFactory() = viewModelFactory{ container, savedStateHandle -> TaskScreenViewModel(savedStateHandle, container.taskRepository) }

    private inline fun <reified T: ViewModel> viewModelFactory(
        crossinline viewModelInitializer: (AppContainer, SavedStateHandle) -> T
    ): ViewModelProvider.Factory  = androidx.lifecycle.viewmodel.viewModelFactory {
        initializer {
            val application = this[APPLICATION_KEY] as ToDoApplication
            val container = application.container

            val savedStateHandle = createSavedStateHandle()
            viewModelInitializer(container, savedStateHandle)
        }
    }
}