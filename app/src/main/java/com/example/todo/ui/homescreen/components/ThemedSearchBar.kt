package com.example.todo.ui.homescreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.homescreen.DropDownMenuCategory
import com.example.todo.ui.theme.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun ThemedSearchBar(
    query: String,
    isDropDownMenuExpanded: Boolean,
    categories: List<DropDownMenuCategory>,
    noinline onQueryChange: (String) -> Unit,
    noinline onSearch: (String) -> Unit,
    noinline onSearchOptionClick: () -> Unit,
    noinline onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchBar(
        inputField = {
            InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = false,
                placeholder = { Text(text = stringResource(R.string.search)) },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.kebab_icon),
                        contentDescription = stringResource(R.string.search_option),
                        modifier = Modifier
                            .clickable(onClick = onSearchOptionClick)
                    )

                    SearchOptionDropDown(
                        expanded = isDropDownMenuExpanded,
                        categories = categories,
                        onDismiss = onDismiss
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AppColor.OnSurfaceAndBackground,
                    unfocusedTextColor = AppColor.OnSurfaceAndBackground,
                    cursorColor = AppColor.ButtonOutline,
                    focusedContainerColor = AppColor.Surface,
                    unfocusedContainerColor = AppColor.Surface,
                    focusedPlaceholderColor = AppColor.OnSurfaceAndBackground,
                    unfocusedPlaceholderColor = AppColor.OnSurfaceAndBackground,
                    focusedTrailingIconColor = AppColor.OnSurfaceAndBackground,
                    unfocusedTrailingIconColor = AppColor.OnSurfaceAndBackground
                ),
                onExpandedChange = { },
                modifier = modifier
                    .border(
                        width = 2.dp,
                        color = AppColor.Outline,
                        shape = RoundedCornerShape(40.dp)
                    )
            )
        },
        expanded = false,
        onExpandedChange = {},
        content = {}
    )
}