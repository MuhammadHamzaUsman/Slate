package com.example.todo.ui.util

import androidx.compose.ui.graphics.Color

fun Color.isBelowThreshold(threshold: Int): Boolean {
    val normalisedValue = threshold / 255.0

    return red <= normalisedValue ||
        green <= normalisedValue ||
        blue <= normalisedValue
}

