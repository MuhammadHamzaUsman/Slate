package com.example.todo.ui.componenets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.todo.R
import com.example.todo.ui.theme.AppColor

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    size: Dp,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(R.drawable.back_icon),
        contentDescription = stringResource(R.string.close_drawer),
        tint = AppColor.OnSurfaceAndBackground,
        modifier = modifier
            .size(size)
            .clickable(onClick = onClick)
    )
}