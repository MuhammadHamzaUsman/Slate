package com.example.todo.ui.drawer

interface InputDialogRules{
    companion object{
        const val MAX_LENGTH = 20
        fun isDialogSaveEnabled(text: String): Boolean = text.isNotBlank()
        fun isDialogError(text: String): Boolean = text.length > MAX_LENGTH
    }
}