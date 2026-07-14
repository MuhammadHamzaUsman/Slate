package com.example.todo.data.di

import com.example.todo.data.repository.TaskRepository

interface AppContainer {
    val taskRepository: TaskRepository
}