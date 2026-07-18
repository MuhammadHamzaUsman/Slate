package com.example.todo.ui.drawer.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.todo.R
import com.example.todo.ui.drawer.DrawerUiState
import com.example.todo.ui.drawer.InputDialogRules
import com.example.todo.ui.theme.AppColor
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun InputDialog(
    onSave: (String, Color) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    var textField by rememberSaveable { mutableStateOf("") }
    var isColorPickerOpen by rememberSaveable { mutableStateOf(false) }
    val controller = rememberColorPickerController()
    val color by controller.selectedColor

    Dialog(
        onDismissRequest = onDismissRequest
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .width(320.dp)
                .background(
                    color = AppColor.Surface,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 2.dp,
                    color = AppColor.Outline,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                ThemedTextField(
                    text = textField,
                    onTextChange = { textField = it },
                    iconColor = color,
                    onIconClick = { isColorPickerOpen = !isColorPickerOpen },
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(R.drawable.save_icon),
                    contentDescription = stringResource(R.string.save),
                    tint = if(InputDialogRules.isDialogSaveEnabled(textField)) AppColor.OnSurfaceAndBackground
                        else AppColor.ButtonOutline,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(40.dp)
                        .clickable(
                            enabled = InputDialogRules.isDialogSaveEnabled(textField)
                        ) {
                            onSave(
                                textField,
                                if (color == Color.Transparent) DrawerUiState.INITIAL_COLOR else color
                            )
                        }
                )
            }

            AnimatedVisibility(
                visible = isColorPickerOpen,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(
                            top = 0.dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                ) {
                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        controller = controller,
                        initialColor = DrawerUiState.INITIAL_COLOR
                    )

                    BrightnessSlider(
                        borderSize = 2.dp,
                        borderColor = AppColor.Outline,
                        controller = controller,
                        wheelRadius = 5.dp,
                        initialColor = DrawerUiState.INITIAL_COLOR,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemedTextField(
    text: String,
    onTextChange: (String) -> Unit,
    iconColor: Color,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text("Name") },
        isError = InputDialogRules.isDialogError(text),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = AppColor.OnSurfaceAndBackground,
            unfocusedTextColor = AppColor.OnSurfaceAndBackground,
            errorTextColor = AppColor.Error,
            focusedContainerColor = AppColor.Surface,
            disabledContainerColor = AppColor.Surface,
            errorContainerColor = AppColor.Surface,
            focusedBorderColor = AppColor.Outline,
            unfocusedBorderColor = AppColor.Outline,
            errorBorderColor = AppColor.Error,
            cursorColor = AppColor.ButtonOutline,
            focusedLabelColor = AppColor.OnSurfaceAndBackground,
            unfocusedLabelColor = AppColor.OnSurfaceAndBackground,
            errorLabelColor = AppColor.Error,
            focusedSupportingTextColor = AppColor.OnSurfaceAndBackground,
            unfocusedSupportingTextColor = AppColor.OnSurfaceAndBackground,
            errorSupportingTextColor = AppColor.Error
        ),
        trailingIcon = {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = if(iconColor == Color.Transparent) DrawerUiState.INITIAL_COLOR else iconColor,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .clickable(onClick = onIconClick)
            )
        },
        supportingText = {
            Text(
                text = stringResource(
                    R.string.max_characters,
                    text.length,
                    InputDialogRules.MAX_LENGTH
                ),
                color = if(InputDialogRules.isDialogError(text)) AppColor.Error else AppColor.OnSurfaceAndBackground
            )
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun InputDialogPreview() {
    InputDialog(
        onSave = {_,_ ->},
        onDismissRequest = {},
    )
}