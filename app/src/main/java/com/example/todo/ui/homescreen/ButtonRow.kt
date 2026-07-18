package com.example.todo.ui.homescreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.todo.ui.componenets.Label
import com.example.todo.ui.theme.AppColor
import com.example.todo.ui.util.applyIf
import com.example.todo.ui.util.isBelowThreshold
import com.example.todo.ui.util.toColor

@Composable
inline fun ButtonRow(
    stage: Stage?,
    category: Category?,
    noinline onStageClicked: () -> Unit,
    noinline onCategoryClicked: () -> Unit,
    noinline onDeleteClicked: () -> Unit,
    noinline onRemoveFilterClicked: () -> Unit,
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
            ButtonRowLabel(
                text = category?.name ?: stringResource(R.string.category),
                color = category?.color?.toColor() ?: AppColor.Surface,
                filter = category != null,
                onClick = onCategoryClicked,
                modifier = Modifier.fillMaxHeight()
            )

            ButtonRowLabel(
                text = stage?.name ?: stringResource(R.string.stage),
                color = stage?.color?.toColor() ?: AppColor.Surface,
                filter = stage != null,
                onClick = onStageClicked,
                modifier = Modifier.fillMaxHeight()
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(30.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.filter_off_icon),
                contentDescription = stringResource(R.string.remove_all_filter),
                tint = AppColor.OnSurfaceAndBackground,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxHeight()
                    .clickable(onClick = onRemoveFilterClicked)
            )

            Icon(
                painter = painterResource(R.drawable.delete_icon),
                contentDescription = stringResource(R.string.delete_button_to_select_and_the_delete),
                tint = AppColor.OnSurfaceAndBackground,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxHeight()
                    .clickable(onClick = onDeleteClicked)
            )
        }
    }
}

@Composable
fun ButtonRowLabel(
    text: String,
    color: Color,
    filter: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Label(
        text = text,
        textColor = if (filter) {
            if (color.isBelowThreshold(50)) AppColor.OnSurfaceAndBackground
            else AppColor.Background
        } else {
            AppColor.OnSurfaceAndBackground
        },
        fontSize = 14.sp,
        backgroundColor = color,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        cornerRadius = 6.dp,
        modifier = modifier
            .applyIf(!filter) { modifier ->
                modifier.border(
                    width = 2.dp,
                    color = AppColor.Outline,
                    shape = RoundedCornerShape(6.dp)
                )
            }
            .clickable(onClick = onClick)
    )
}

@Preview
@Composable
private fun ButtonRowPreview() {
    ButtonRow(
        onStageClicked = {},
        onCategoryClicked = {},
        onDeleteClicked = {},
        onRemoveFilterClicked = {},
        stage = Stage.COMPLETED_STAGE,
        category = Category.DEFAULT_CATEGORY,
    )
}