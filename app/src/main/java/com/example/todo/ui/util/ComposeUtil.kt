package com.example.todo.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.todo.ToDoApplication

@Composable
fun appContainer() = (LocalContext.current.applicationContext as ToDoApplication).container

fun Modifier.applyIf(boolean: Boolean, action: (Modifier) -> Modifier) = if(boolean) action(this) else this

fun Long.toColor() = Color(this)