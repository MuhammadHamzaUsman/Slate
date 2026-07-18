package com.example.todo.ui.drawer

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.ui.componenets.BackButton
import com.example.todo.ui.componenets.ThemedDialog
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
    onDialogDeleteCategory: (Category) -> Unit,
    onDialogSaveStage: (String, Color) -> Unit,
    onDialogDeleteStage: (Stage) -> Unit,
    onDialogDismiss: () -> Unit,

    onUpClicked: () -> Unit,

    modifier: Modifier = Modifier
) {
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
        BackButton(size = 28.dp) { onUpClicked() }

        HeaderRow(
            header = stringResource(R.string.category),
            removeSelected = drawerState.removalType == RemovalType.REMOVE_CATEGORY,
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
            removeSelected = drawerState.removalType == RemovalType.REMOVE_STAGE,
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

    when(val type = drawerState.dialogType){
        ActionType.NONE -> { }
        ActionType.ADD_CATEGORY -> InputDialog(
            onSave = onDialogSaveCategory,
            onDismissRequest = onDialogDismiss
        )
        ActionType.ADD_STAGE -> InputDialog(
            onSave = onDialogSaveStage,
            onDismissRequest = onDialogDismiss
        )

        is ActionType.REMOVE_CATEGORY -> ThemedDialog(
            message = "Deleting ${type.category.name} category will move all of its tasks to 'Uncategorized'.",
            onYes = { onDialogDeleteCategory(type.category) },
            onNo = onDialogDismiss,
            onDismiss = onDialogDismiss
        )
        is ActionType.REMOVE_STAGE -> ThemedDialog(
            message = "Deleting ${type.stage.name} stage will move all of its tasks to 'Incomplete'.",
            onYes = { onDialogDeleteStage(type.stage) },
            onNo = onDialogDismiss,
            onDismiss = onDialogDismiss
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
        onDialogSaveCategory = { _, _ -> },
        onDialogSaveStage = { _, _ -> },
        onDialogDismiss = {},
        onUpClicked = {},
        onDialogDeleteCategory = {},
        onDialogDeleteStage = {},
    )
}