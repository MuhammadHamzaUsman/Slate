package com.example.todo.data.model

import com.example.todo.domain.model.SortOption

val SortOption.dataBaseColumn: String
    get() = when(this){
        SortOption.NONE -> TaskEntity::id.name
        SortOption.CREATED -> TaskEntity::createdAt.name
        SortOption.UPDATED -> TaskEntity::updatedAt.name
    }