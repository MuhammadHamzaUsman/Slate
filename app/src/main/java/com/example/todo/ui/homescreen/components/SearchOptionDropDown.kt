package com.example.todo.ui.homescreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.ui.homescreen.DropDownMenuCategory
import com.example.todo.ui.homescreen.DropDownMenuOption
import com.example.todo.ui.theme.AppColor

@Composable
fun SearchOptionDropDown(
    expanded: Boolean,
    categories: List<DropDownMenuCategory>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        shape = RoundedCornerShape(6.dp),
        containerColor = AppColor.Surface,
        border = BorderStroke(
            width = 2.dp,
            color = AppColor.Outline
        ),
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = modifier
            .width(IntrinsicSize.Max)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ){
            categories.forEachIndexed { index, category ->
                if(index != 0){
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = AppColor.ButtonOutline
                    )
                }

                DropdownMenuCategory(
                    category = category,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DropdownMenuCategory(
    category: DropDownMenuCategory,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(category.icon),
                contentDescription = stringResource(
                    R.string.drop_down_menu_category_icon,
                    category.header
                ),
                tint = AppColor.OnSurfaceAndBackground
            )

            Text(
                text = category.header,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = AppColor.OnSurfaceAndBackground
            )
        }

        category.options.forEach { option ->
            Text(
                text = option.label,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = AppColor.OnSurfaceAndBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = option.onClick)
                    .padding(vertical = 6.dp)
            )
        }
    }
}

@Preview
@Composable
private fun DropdownMenuCategoryPreview() {
    SearchOptionDropDown(
        expanded = true,
        categories = listOf(
            DropDownMenuCategory(
                header = "Search Option",
                icon = R.drawable.search_gear_icon,
                options = listOf(
                    DropDownMenuOption(
                        label = "Title",
                        onClick = {}
                    )
                )
            )
        ),
        onDismiss = {  }
    )
}