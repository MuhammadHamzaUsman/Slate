package com.example.todo.ui.homescreen

import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage

data class SearchState(
    val query: String = "",
    val category: Category? = null,
    val stage: Stage? = null
)