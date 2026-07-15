package com.example.todo.ui.homescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.model.Task
import com.example.todo.ui.componenets.Label
import com.example.todo.ui.theme.AppColor

@Composable
fun TaskListItem(
    modifier: Modifier = Modifier,
    task: Task,
    isSelected: Boolean,
    onActionButtonClicked: (Task) -> Unit,
    onItemClicked: (Task) -> Unit,
    onLongPress: (Task) -> Unit
) {
    val color by animateColorAsState(if(isSelected) AppColor.OnSurfaceAndBackground else AppColor.Outline,)

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Card(
        colors = CardDefaults.cardColors(containerColor = AppColor.Surface),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 2.dp,
            color = color
        ),
        modifier = modifier
            .combinedClickable(
                onClick = { onItemClicked(task) },
                onLongClick = { onLongPress(task) }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(
                        width = 2.dp,
                        color = AppColor.ButtonOutline,
                        shape = CircleShape
                    )
                    .background(AppColor.Surface)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = ripple(radius = 24.dp),
                        onClick = { onActionButtonClicked(task) }
                    )
                    .clip(CircleShape)
            ) {
                this@Row.AnimatedVisibility(
                    visible = isPressed,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        content = {},
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxSize()
                            .background(
                                color = AppColor.ButtonOutline,
                                shape = CircleShape
                            )
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(80.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ListLabel(
                        name = task.category.name,
                        color = task.category.color,
                    )

                    ListLabel(
                        name = task.stage.name,
                        color = task.stage.color
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = task.title,
                        color = AppColor.OnSurfaceAndBackground,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = task.description,
                        color = AppColor.OnSurfaceAndBackground,
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun ListLabel(
    name: String,
    color: Long,
    modifier: Modifier = Modifier
) {
    Label(
        text = name,
        textColor = AppColor.Surface,
        fontSize = 12.sp,
        backgroundColor = Color(color),
        contentPadding = PaddingValues(horizontal = 4.dp),
        cornerRadius = 4.dp,
        modifier = modifier
    )
}


@Preview
@Composable
private fun TaskListItemPreview() {
    TaskListItem(
        task = Task(
            title = "Lorem ipsum dolor sit amet",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
            category = Category.DEFAULT_CATEGORY,
            stage = Stage.INCOMPLETE_STAGE
        ),
        onActionButtonClicked = {},
        onItemClicked = {},
        onLongPress = {},
        isSelected = true
    )
}