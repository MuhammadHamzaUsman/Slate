package com.example.todo.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.todo.ToDoApplication

@Composable
fun appContainer() = (LocalContext.current.applicationContext as ToDoApplication).container