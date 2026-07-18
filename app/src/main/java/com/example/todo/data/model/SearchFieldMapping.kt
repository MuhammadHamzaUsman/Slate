package com.example.todo.data.model

import com.example.todo.domain.model.SearchField

val SearchField.dataBaseColumn: String
    get() = when(this){
        SearchField.TITLE -> TaskEntity::title.name
        SearchField.DESCRIPTION -> TaskEntity::description.name
    }