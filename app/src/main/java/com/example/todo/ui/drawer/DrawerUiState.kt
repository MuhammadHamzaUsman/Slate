package com.example.todo.ui.drawer

import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage

data class DrawerUiState(
    val removing: ActionType = ActionType.NONE,
    val dialogType: ActionType = ActionType.NONE,
    val selectedCategory: Category? = null,
    val selectedStage: Stage? = null,
    val categories: List<Category> = emptyList(),
    val stages: List<Stage> = emptyList()
){
    val hasSelection = selectedCategory != null && selectedStage != null
}

enum class ActionType{
    NONE, CATEGORY, STAGE
}