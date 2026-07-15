package com.example.todo.ui.homescreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.ui.componenets.Label
import com.example.todo.ui.theme.AppColor

@Composable
inline fun ButtonRow(
    noinline onStageClicked: () -> Unit,
    noinline onCategoryClicked: () -> Unit,
    noinline onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ButtonRowLabel(
                text = stringResource(R.string.stage),
                onClick = onStageClicked,
                modifier = Modifier.fillMaxHeight()
            )

            ButtonRowLabel(
                text = stringResource(R.string.categories),
                onClick = onCategoryClicked,
                modifier = Modifier.fillMaxHeight()
            )
        }

        Icon(
            painter = painterResource(R.drawable.delete_icon),
            contentDescription = "Delete Button to select and the delete",
            tint = AppColor.OnSurfaceAndBackground,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxHeight()
                .clickable(onClick = onDeleteClicked)
        )
    }
}

@Composable
fun ButtonRowLabel(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Label(
        text = text,
        textColor = AppColor.OnSurfaceAndBackground,
        fontSize = 14.sp,
        backgroundColor = AppColor.Surface,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        cornerRadius = 6.dp,
        modifier = modifier
            .border(
                width = 2.dp,
                color = AppColor.Outline,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable(onClick = onClick)
    )
}

@Preview
@Composable
private fun ButtonRowPreview() {
    ButtonRow(
        onStageClicked = {},
        onCategoryClicked = {},
        onDeleteClicked = {}
    )
}