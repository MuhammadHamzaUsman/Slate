package com.example.todo.ui.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.theme.AppColor

@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(60.dp)
            .clickable(onClick = onClick)
            .background(
                color = AppColor.Surface,
                shape = CircleShape
            )
            .border(
                width = 2.dp,
                color = AppColor.OnSurfaceAndBackground,
                shape = CircleShape
            )
    ){
        Icon(
            painter = painterResource(R.drawable.add_icon),
            contentDescription = stringResource(R.string.add_new_task),
            tint = AppColor.OnSurfaceAndBackground,
            modifier = Modifier
                .size(30.dp)
        )
    }
}

@Preview
@Composable
private fun FloatingActionButtonPreview() {
    FloatingActionButton{}
}