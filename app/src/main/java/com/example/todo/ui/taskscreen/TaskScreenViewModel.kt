package com.example.todo.ui.taskscreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.repository.TaskRepository
import com.example.todo.ui.navigation.AppRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class TaskScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
): ViewModel() {
    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState())
    val taskState = _taskState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TaskState()
        )

    init {
        val route = savedStateHandle.toRoute<AppRoute.TaskScreen>()
        val taskId = route.taskId

        if(taskId != null){
            viewModelScope.launch(Dispatchers.IO) {
                taskRepository.getTask(taskId)?.let { task ->
                    _taskState.update { task.toTaskState(it.saving) }
                }
            }
        }

        viewModelScope.launch {
            while (true){
                delay(SAVE_TIME_GAP.milliseconds)
                saveTask()
            }
        }
    }

    fun updateTitle(title: String){
        _taskState.update { it.copy(title = title) }
    }

    fun updateDescription(description: String){
        _taskState.update { it.copy(description = description) }
    }

    fun updateCategory(category: Category){
        _taskState.update { it.copy(category = category) }
    }

    fun updateStage(stage: Stage){
        _taskState.update { it.copy(stage = stage) }
    }

    private suspend fun saveTask(){
        _taskState.update { it.copy(saving = true) }

        val task = taskState.value.toTask()
        if(taskState.value.id == null){
            val id = taskRepository.insertTask(task)
            _taskState.update { it.copy(id = id) }
        }
        else{
            taskRepository.updateTask(task)
        }

        _taskState.update { it.copy(saving = false) }
    }

    fun saveTask(context: Context){
        viewModelScope.launch {
            saveTask()

            val toast = Toast(context).apply {
                setText("Task Saved")
            }

            toast.show()
        }
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5000L
        private const val SAVE_TIME_GAP = 10000
    }
}