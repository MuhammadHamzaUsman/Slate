package com.example.todo.ui.componenets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.AppColor

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .size(size)
            .border(
                width = 2.dp,
                color = AppColor.ButtonOutline,
                shape = CircleShape
            )
            .background(
                color = AppColor.Surface,
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(radius = size),
                onClick = onClick
            )
    ) {
        AnimatedVisibility(
            visible = isPressed,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                content = {},
                modifier = Modifier
                    .padding(6.dp)
                    .background(
                        color = AppColor.ButtonOutline,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview
@Composable
private fun CircleButtonPreview() {
    CircleButton{}
}