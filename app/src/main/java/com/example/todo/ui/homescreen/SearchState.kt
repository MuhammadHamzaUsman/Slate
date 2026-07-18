package com.example.todo.ui.homescreen

import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.model.SearchField
import com.example.todo.domain.model.SortOption
import com.example.todo.domain.model.SortOrder

data class SearchState(
    val query: String = "",
    val category: Category? = null,
    val stage: Stage? = null,
    val sortOption: SortOption = SortOption.NONE,
    val sortOrder: SortOrder = SortOrder.ASCENDING,
    val searchField: SearchField = SearchField.TITLE
)