package com.example.todo.data.model

import com.example.todo.domain.model.SortOrder

val SortOrder.dataBaseKeyword: String
    get() = when(this) {
        SortOrder.ASCENDING -> "ASC"
        SortOrder.DESCENDING -> "DESC"
    }