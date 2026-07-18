package com.example.todo.ui.drawer

import androidx.compose.ui.graphics.Color
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage

data class DrawerUiState(
    val removalType: RemovalType = RemovalType.NONE,
    val dialogType: ActionType = ActionType.NONE,
    val selectedCategory: Category? = null,
    val selectedStage: Stage? = null,
    val categories: List<Category> = emptyList(),
    val stages: List<Stage> = emptyList()
){
    companion object{
        val INITIAL_COLOR = Color.White
    }
}