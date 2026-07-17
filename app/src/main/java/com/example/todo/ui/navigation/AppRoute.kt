package com.example.todo.ui.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute{
    @Serializable
    object HomeScreen: AppRoute

    @Serializable
    data class TaskScreen(
        val taskId: Int?
    )
}