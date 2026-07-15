package com.example.todo.di

import com.example.todo.domain.repository.TaskRepository

interface AppContainer {
    val taskRepository: TaskRepository
}