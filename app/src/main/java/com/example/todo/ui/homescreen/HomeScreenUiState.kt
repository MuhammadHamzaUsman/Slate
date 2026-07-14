package com.example.todo.ui.homescreen

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val taskToBeDeleted: Set<Int> = emptySet(),
    val showingDialog: Boolean = false,
)