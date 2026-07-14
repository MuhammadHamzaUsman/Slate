package com.example.todo.ui.drawer

import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage

data class DrawerUiState(
    val visible: Boolean = false,
    val selectedCategory: Category? = null,
    val selectedStage: Stage? = null,
    val categories: List<Category> = emptyList(),
    val stages: List<Stage> = emptyList()
)