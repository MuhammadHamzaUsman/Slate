package com.example.todo.ui.homescreen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.domain.model.Task
import com.example.todo.ui.componenets.CircleButton
import com.example.todo.ui.componenets.Label
import com.example.todo.ui.theme.AppColor
import com.example.todo.ui.util.applyIf
import com.example.todo.ui.util.isBelowThreshold
import java.time.LocalDateTime

@Composable
fun TaskListItem(
    modifier: Modifier = Modifier,
    task: Task,
    isSelected: Boolean,
    inSelectMode: Boolean,
    onActionButtonClicked: (Task) -> Unit,
    onItemClicked: (Task) -> Unit,
    onLongPress: (Task) -> Unit
) {
    val borderColor by animateColorAsState(if(isSelected) AppColor.OnSurfaceAndBackground else AppColor.Outline,)
    val elevation by animateDpAsState(if(inSelectMode) 9.dp else 0.dp)
    val density = LocalDensity.current

    Card(
        colors = CardDefaults.cardColors(containerColor = AppColor.Surface),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 2.dp,
            color = borderColor
        ),
        modifier = modifier
            .applyIf(inSelectMode) { modifier ->
                modifier.dropShadow(RoundedCornerShape(8.dp)) {
                    color = AppColor.OnSurfaceAndBackground.copy(alpha = 0.3f)

                    with(density) {
                        radius = elevation.toPx()
                    }
                }
            }
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
            CircleButton{
                onActionButtonClicked(task)
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
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis
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
    val color = Color(color)

    Label(
        text = name,
        textColor = if (color.isBelowThreshold(50)) AppColor.OnSurfaceAndBackground
        else AppColor.Background,
        fontSize = 12.sp,
        backgroundColor = color,
        contentPadding = PaddingValues(horizontal = 4.dp),
        cornerRadius = 4.dp,
        modifier = modifier
    )
}


@Preview()
@Composable
private fun TaskListItemPreview() {
    TaskListItem(
        task = Task(
            title = "Lorem ipsum dolor sit amet",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
            category = Category.DEFAULT_CATEGORY,
            stage = Stage.INCOMPLETE_STAGE,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        ),
        onActionButtonClicked = {},
        onItemClicked = {},
        onLongPress = {},
        isSelected = true,
        inSelectMode = true,
    )
}