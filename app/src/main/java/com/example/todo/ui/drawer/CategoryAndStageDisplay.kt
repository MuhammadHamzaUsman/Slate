package com.example.todo.ui.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.data.model.Category
import com.example.todo.ui.componenets.Label
import com.example.todo.ui.theme.AppColor
import com.example.todo.ui.util.isBelowThreshold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> CategoryAndStageDisplay(
    items: List<T>,
    text: (T) -> String,
    color: (T) -> Long,
    onClick: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        items.forEach {
            val color = Color(color(it))

            Label(
                text = text(it),
                textColor = if(color.isBelowThreshold(50)) AppColor.OnSurfaceAndBackground else AppColor.Background,
                fontSize = 14.sp,
                backgroundColor = color,
                contentPadding = PaddingValues(8.dp),
                cornerRadius = 6.dp,
                modifier = Modifier
                    .clickable{ onClick(it) }
            )
        }
    }
}

@Preview
@Composable
private fun CategoryAndStageDisplayPreview() {
    val categories = listOf(
        Category("Category 1", 0xFF76B3B3),
        Category("Category 2", 0xFF5BD269),
        Category("Category 3", 0xFF6760AC),
        Category("Category 4", 0xFFCAAC6A),
        Category("Category 5", 0xFF97D947),
        Category("Category 6", 0xFFBF5454),
        Category("Category 7", 0xFF552F8B),
    )

    CategoryAndStageDisplay(
        items = categories,
        onClick = {},
        text = Category::name,
        color = Category::color,
        modifier = Modifier.fillMaxWidth()
    )
}