package com.example.todo.ui.drawer

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
            uiState.copy(categories = categories, stages = stages)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DrawerUiState()
        )

    fun openDrawer(){
        _uiState.update { it.copy(visible = true) }
    }

    fun closeDrawer(){
        _uiState.update { it.copy(visible = false) }
    }

    fun selectCategory(category: Category){
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun unSelectCategory(){
         _uiState.update { it.copy(selectedCategory = null) }
    }

    fun selectStage(stage: Stage){
        _uiState.update { it.copy(selectedStage = stage) }
    }

    fun unSelectStage(){
        _uiState.update { it.copy(selectedStage = null) }
    }

    fun addCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO) { taskRepository.addCategory(category) }
    }

    fun removeCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO) { taskRepository.removeCategory(category) }
    }

    fun addStage(stage: Stage){
        viewModelScope.launch(Dispatchers.IO) { taskRepository.addStage(stage) }
    }

    fun removeStage(stage: Stage){
        viewModelScope.launch(Dispatchers.IO) { taskRepository.removeStage(stage) }
    }
}