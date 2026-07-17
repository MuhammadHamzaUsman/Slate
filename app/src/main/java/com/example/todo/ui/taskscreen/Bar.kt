package com.example.todo.ui.taskscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.ui.componenets.CircleButton
import com.example.todo.ui.componenets.Label
import com.example.todo.ui.theme.AppColor
import com.example.todo.ui.util.toColor

@Composable
inline fun Bar(
    stage: Stage,
    category: Category,
    saveEnabled: Boolean,
    noinline onStageClicked: () -> Unit,
    noinline onCategoryClicked: () -> Unit,
    noinline onSaveClicked: () -> Unit,
    noinline onCompleteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(30.dp)
        ) {
            BarLabel(
                text = stage.name,
                color = stage.color.toColor(),
                onClick = onStageClicked,
                modifier = Modifier.fillMaxHeight()
            )

            BarLabel(
                text = category.name,
                color = category.color.toColor(),
                onClick = onCategoryClicked,
                modifier = Modifier.fillMaxHeight()
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(30.dp)
        ) {
            CircleButton(size = 30.dp) {
                onCompleteClicked()
            }

            Icon(
                painter = painterResource(R.drawable.save_icon),
                contentDescription = stringResource(R.string.save),
                tint = if(saveEnabled) AppColor.OnSurfaceAndBackground else AppColor.ButtonOutline,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxHeight()
                    .clickable(
                        enabled = saveEnabled,
                        onClick = onSaveClicked
                    )
            )

        }
    }
}

@Composable
fun BarLabel(
    text: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(text) { text ->
        Label(
            text = text,
            textColor = AppColor.Background,
            fontSize = 14.sp,
            backgroundColor = color,
            contentPadding = PaddingValues(4.dp),
            cornerRadius = 6.dp,
            modifier = modifier.clickable(onClick = onClick)
        )
    }
}

@Preview
@Composable
private fun BarPreview() {
    Bar(
        stage = Stage.COMPLETED_STAGE,
        category = Category.DEFAULT_CATEGORY,
        saveEnabled = true,
        onStageClicked = {},
        onCategoryClicked = {},
        onSaveClicked = {},
        onCompleteClicked = {},
    )
}