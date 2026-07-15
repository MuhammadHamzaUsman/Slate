package com.example.todo.ui.taskscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.model.Task
import com.example.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskScreenViewModel(
    task: Task? = null,
    private val taskRepository: TaskRepository
): ViewModel() {
    private val _noteState: MutableStateFlow<TaskState> = MutableStateFlow(task?.toTaskState() ?: TaskState())
    val noteState = _noteState.asStateFlow()

    fun updateTitle(title: String){
        _noteState.update { it.copy(title = title) }
    }

    fun updateDescription(description: String){
        _noteState.update { it.copy(description = description) }
    }

    fun updateCategory(category: Category){
        _noteState.update { it.copy(category = category) }
    }

    fun updateStage(stage: Stage){
        _noteState.update { it.copy(stage = stage) }
    }

    fun saveNote(){
        viewModelScope.launch {
            _noteState.update { it.copy(saving = true) }

            val task = noteState.value.toTask()
            if(noteState.value.id == null){
                val id = taskRepository.insertTask(task)
                _noteState.update { it.copy(id = id) }
            }
            else{
                taskRepository.updateTask(task)
            }

            _noteState.update { it.copy(saving = false) }
        }
    }
}