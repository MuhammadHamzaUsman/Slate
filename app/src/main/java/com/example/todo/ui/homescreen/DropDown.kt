package com.example.todo.ui.homescreen

import androidx.annotation.DrawableRes

data class DropDownMenuCategory(
    val header: String,
    @DrawableRes val icon: Int,
    val options: List<DropDownMenuOption>
)

data class DropDownMenuOption(
    val label: String,
    val onClick: () -> Unit
)