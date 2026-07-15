package com.example.todo.ui.homescreen

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun ThemedSearchBar(
    query: String,
    noinline onQueryChange: (String) -> Unit,
    noinline onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        inputField = {
            InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = false,
                placeholder = { Text(text = "Search Title") },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AppColor.OnSurfaceAndBackground,
                    unfocusedTextColor = AppColor.OnSurfaceAndBackground,
                    cursorColor = AppColor.OnSurfaceAndBackground,
                    focusedContainerColor = AppColor.Surface,
                    unfocusedContainerColor = AppColor.Surface,
                    focusedPlaceholderColor = AppColor.OnSurfaceAndBackground,
                    unfocusedPlaceholderColor = AppColor.OnSurfaceAndBackground
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