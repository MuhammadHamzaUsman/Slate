package com.example.todo.ui.drawer

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.ui.componenets.Label
import com.example.todo.ui.theme.AppColor
import com.example.todo.ui.util.applyIf

@Composable
fun HeaderRow(
    header: String,
    removeSelected: Boolean,
    onPlus: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(35.dp)
    ) {
        Label(
            text = header,
            textColor = AppColor.OnSurfaceAndBackground,
            fontSize = 14.sp,
            backgroundColor = AppColor.Surface,
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            cornerRadius = 6.dp,
            modifier = Modifier
                .fillMaxHeight()
                .border(
                    width = 2.dp,
                    color = AppColor.Outline,
                    shape = RoundedCornerShape(6.dp)
                )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPlus,
                colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent)
            ){
                Icon(
                    painter = painterResource(R.drawable.add_icon),
                    contentDescription = stringResource(R.string.add_header, header),
                    tint = AppColor.OnSurfaceAndBackground,
                )
            }

            AnimatedContent(removeSelected) { removeSelected ->
                IconButton(
                    onClick = onRemove,
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.remove_icon),
                        contentDescription = stringResource(R.string.remove_header, header),
                        tint = if (removeSelected) AppColor.Surface else AppColor.OnSurfaceAndBackground,
                        modifier = Modifier
                            .applyIf(removeSelected) { modifier ->
                                modifier
                                    .background(
                                        color = AppColor.OnSurfaceAndBackground,
                                        shape = CircleShape
                                    )
                                    .padding(1.dp)
                            }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HeaderRowPreview() {
    HeaderRow(
        header = "Category",
        removeSelected = true,
        onPlus = { },
        onRemove = { }
    )
}