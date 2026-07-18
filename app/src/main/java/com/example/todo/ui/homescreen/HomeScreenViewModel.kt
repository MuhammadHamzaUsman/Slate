package com.example.todo.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.R
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.model.SearchField
import com.example.todo.domain.model.SortOption
import com.example.todo.domain.model.SortOrder
import com.example.todo.domain.model.Task
import com.example.todo.domain.repository.TaskRepository
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

    fun setSortOption(sortOption: SortOption){
        _searchState.update { state -> state.copy(sortOption = sortOption) }
    }

    fun setSortOrder(sortOrder: SortOrder){
        _searchState.update { state -> state.copy(sortOrder = sortOrder) }
    }

    fun setSearchField(searchField: SearchField){
        _searchState.update { state -> state.copy(searchField = searchField) }
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

    fun showDialogBox(){
         _uiState.update { state -> state.copy(showingDialog = true) }
    }

    fun dismissDialogBox(){
        _uiState.update { state -> state.copy(showingDialog = false) }
    }

    fun openDropDownMenu(){
        _uiState.update { it.copy(isDropDownExpanded = true) }
    }

    fun closeDropDownMenu(){
        _uiState.update { it.copy(isDropDownExpanded = false) }
    }

    val CATEGORIES: List<DropDownMenuCategory> = listOf(
        DropDownMenuCategory(
            header = "Sort Option",
            icon = R.drawable.sort_icon,
            options = listOf(
                DropDownMenuOption(
                    label = "None",
                    onClick = {
                        setSortOption(SortOption.NONE)
                        closeDropDownMenu()
                    }
                ),

                DropDownMenuOption(
                    label = "Date Created",
                    onClick = {
                        setSortOption(SortOption.CREATED)
                        closeDropDownMenu()
                    }
                ),

                DropDownMenuOption(
                    label = "Recently Updated",
                    onClick = {
                        setSortOption(SortOption.UPDATED)
                        closeDropDownMenu()
                    }
                )
            )
        ),

        DropDownMenuCategory(
            header = "Sort Order",
            icon = R.drawable.sort_order_icon,
            options = listOf(
                DropDownMenuOption(
                    label = "Ascending",
                    onClick = {
                        setSortOrder(SortOrder.ASCENDING)
                        closeDropDownMenu()
                    }
                ),

                DropDownMenuOption(
                    label = "Descending",
                    onClick = {
                        setSortOrder(SortOrder.DESCENDING)
                        closeDropDownMenu()
                    }
                )
            )
        ),

        DropDownMenuCategory(
            header = "Search Field",
            icon = R.drawable.search_gear_icon,
            options = listOf(
                DropDownMenuOption(
                    label = "Title",
                    onClick = {
                        setSearchField(SearchField.TITLE)
                        closeDropDownMenu()
                    }
                ),

                DropDownMenuOption(
                    label = "Description",
                    onClick = {
                        setSearchField(SearchField.DESCRIPTION)
                        closeDropDownMenu()
                    }
                )
            )
        )
    )

    companion object {
        private const val TYPING_DEBOUNCE_MILLIS = 500
        private const val TIMEOUT_MILLIS = 5000L
    }
}