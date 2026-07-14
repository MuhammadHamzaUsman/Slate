package com.example.todo

import android.app.Application
import com.example.todo.data.di.AppContainer
import com.example.todo.data.di.DefaultAppContainer

class ToDoApplication: Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}