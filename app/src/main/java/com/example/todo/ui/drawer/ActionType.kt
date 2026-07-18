package com.example.todo.ui.drawer

import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage

sealed interface ActionType {
    object NONE: ActionType
    object ADD_CATEGORY: ActionType
    object ADD_STAGE: ActionType

    data class REMOVE_CATEGORY(val category: Category): ActionType
    data class REMOVE_STAGE(val stage: Stage): ActionType
}