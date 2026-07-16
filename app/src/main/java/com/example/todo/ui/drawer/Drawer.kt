package com.example.todo.ui.drawer

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.ui.theme.AppColor
import com.example.todo.ui.util.previewTaskRepository

@Composable
fun Drawer(
    drawerState: DrawerUiState,

    onCategoryPlus: () -> Unit,
    onCategoryRemove: () -> Unit,
    onCategoryItemClicked: (Category) -> Unit,

    onStagePlus: () -> Unit,
    onStageRemove: () -> Unit,
    onStageItemClicked: (Stage) -> Unit,

    onDialogSaveCategory: (String, Color) -> Unit,
    onDialogSaveStage: (String, Color) -> Unit,
    onDialogDismiss: () -> Unit,

    onUpClicked: () -> Unit,
    onBack: () -> Unit,

    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }
    val shape = remember {
        RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 16.dp,
            bottomEnd = 16.dp,
            bottomStart = 0.dp
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxHeight()
            .background(
                color = AppColor.Surface,
                shape = shape
            )
            .border(
                width = 2.dp,
                color = AppColor.Outline,
                shape = shape
            )
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.back_icon),
            contentDescription = stringResource(R.string.close_drawer),
            tint = AppColor.OnSurfaceAndBackground,
            modifier = Modifier
                .size(28.dp)
                .clickable(onClick = onUpClicked)
                .align(Alignment.Start)
        )

        HeaderRow(
            header = stringResource(R.string.category),
            removeSelected = drawerState.removing == ActionType.CATEGORY,
            onPlus = onCategoryPlus,
            onRemove = onCategoryRemove
        )

        CategoryAndStageDisplay(
            items = drawerState.categories,
            text = Category::name,
            color = Category::color,
            onClick = onCategoryItemClicked,
            modifier = Modifier
                .fillMaxWidth()
                .weight(
                    weight = 1f,
                    fill = false
                )
        )

        HeaderRow(
            header = stringResource(R.string.stage),
            removeSelected = drawerState.removing == ActionType.STAGE,
            onPlus = onStagePlus,
            onRemove = onStageRemove
        )

        CategoryAndStageDisplay(
            items = drawerState.stages,
            text = Stage::name,
            color = Stage::color,
            onClick = onStageItemClicked,
            modifier = Modifier
                .fillMaxWidth()
                .weight(
                    weight = 1f,
                    fill = false
                )
        )
    }

    when(drawerState.dialogType){
        ActionType.NONE -> { }
        ActionType.CATEGORY -> InputDialog(
            onSave = onDialogSaveCategory,
            onDismissRequest = onDialogDismiss
        )
        ActionType.STAGE -> InputDialog(
            onSave = onDialogSaveStage,
            onDismissRequest = onDialogDismiss
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun DrawerPreview() {
    val viewModel = DrawerViewModel(previewTaskRepository())
    val drawerState by viewModel.uiState.collectAsState()

    Drawer(
        drawerState = drawerState,
        onCategoryPlus = {},
        onCategoryRemove = {},
        onCategoryItemClicked = {},
        onStagePlus = {},
        onStageRemove = {},
        onStageItemClicked = {},
        onDialogSaveCategory = {_,_ ->},
        onDialogSaveStage = {_,_ ->},
        onDialogDismiss = {},
        onUpClicked = {},
        onBack = {}
    )
}