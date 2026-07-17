package com.example.todo.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo.ToDoApplication
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.repository.TaskRepository
import com.example.todo.domain.model.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class HomeScreenViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredTasks = _searchState
        .debounce(TYPING_DEBOUNCE_MILLIS.milliseconds)
        .flatMapLatest { searchState ->
            taskRepository.getFilteredTasks(searchState)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyList()
        )

    fun updateSearchQuery(newQuery: String){
        _searchState.update { state -> state.copy(query = newQuery) }
    }

    fun setCategoryFilter(category: Category){
        _searchState.update { state -> state.copy(category = category) }
    }

    fun resetCategoryFilter(){
        _searchState.update { state -> state.copy(category = null) }
    }

    fun setStageFilter(stage: Stage){
        _searchState.update { state -> state.copy(stage = stage) }
    }

    fun resetStageFilter(){
        _searchState.update { state -> state.copy(stage = null) }
    }

    fun enterSelectMode(){
        val state = _uiState.value

        if(state.showingDialog || state.isLoading) return

        _uiState.update { state ->
            state.copy(isSelecting = true)
        }
    }

    fun addToSelectedTask(task: Task){
        _uiState.update { state ->
            state.copy(
                taskSelected = state.taskSelected + task.id
            )
        }
    }

    fun removeFromSelectedTask(task: Task) {
        _uiState.update {
            it.copy(
                taskSelected = it.taskSelected - task.id
            )
        }
    }

    fun exitSelectingMode(){
        _uiState.update { state ->
            state.copy(
                isSelecting = false,
                taskSelected = emptySet()
            )
        }
    }

    fun deleteTasks(){
        val state = _uiState.value

        if(state.taskSelected.isNotEmpty()){
            viewModelScope.launch {
                _uiState.update { state -> state.copy(isLoading = true) }
                taskRepository.deleteTask(state.taskSelected)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        taskSelected = emptySet(),
                        isSelecting = false
                    )
                }
            }
        }
    }

    fun completeTask(task: Task){
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(stage = Stage.COMPLETED_STAGE))
        }
    }

    fun setShowDialogBox(showingDialog: Boolean){
         _uiState.update { state -> state.copy(showingDialog = showingDialog) }
    }

    companion object {
        private const val TYPING_DEBOUNCE_MILLIS = 500
        private const val TIMEOUT_MILLIS = 5000L
        val FACTORY = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as ToDoApplication
                val container = app.container
                HomeScreenViewModel(container.taskRepository)
            }
        }
    }
}