package com.example.todo.ui.drawer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DrawerViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(DrawerUiState())
    val uiState = combine(
            flow = _uiState,
            flow2 = taskRepository.getCategories(),
            flow3 = taskRepository.getStages()
        ){ uiState, categories, stages ->
            uiState.copy(
                categories = categories,
                stages = stages
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DrawerUiState()
        )


    fun resetSelection() {
        _uiState.update { state ->
            state.copy(
                selectedCategory = null,
                selectedStage = null
            )
        }
    }

    fun selectCategory(category: Category){
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun selectStage(stage: Stage){
        _uiState.update { it.copy(selectedStage = stage) }
    }

    fun enterRemovingMode(type: RemovalType){
        _uiState.update { it.copy(removalType = type) }
    }

    fun exitRemovingMode(){
        _uiState.update { it.copy(removalType = RemovalType.NONE) }
    }

    fun addCategory(name: String, color: Color){
        viewModelScope.launch(Dispatchers.IO) { taskRepository.addCategory(Category(name, color.toArgb().toLong())) }
    }

    suspend fun removeCategory(category: Category){
        if(category == Category.DEFAULT_CATEGORY) return

        taskRepository.removeCategory(category)
    }

    fun addStage(name: String, color: Color){
        viewModelScope.launch(Dispatchers.IO) { taskRepository.addStage(Stage(name, color.toArgb().toLong())) }
    }

    suspend fun removeStage(stage: Stage){
        if(stage == Stage.COMPLETED_STAGE || stage == Stage.INCOMPLETE_STAGE) return

        taskRepository.removeStage(stage)
    }

    fun showDialog(type: ActionType){
        _uiState.update { state -> state.copy(dialogType = type) }
    }

    fun dismissDialog(){
        _uiState.update { state ->
            state.copy(
                dialogType = ActionType.NONE,
                removalType = RemovalType.NONE
            )
        }
    }
}