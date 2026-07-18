package com.example.todo.ui.homescreen

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val isSelecting: Boolean = false,
    val taskSelected: Set<Int> = emptySet(),
    val showingDialog: Boolean = false,
    val isDropDownExpanded: Boolean = false
)