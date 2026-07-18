package com.example.todo.ui.taskscreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todo.R
import com.example.todo.data.model.Stage
import com.example.todo.ui.componenets.BackButton
import com.example.todo.ui.drawer.DrawerUiState
import com.example.todo.ui.theme.AppColor
import com.example.todo.ui.util.previewTaskRepository

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    taskScreenViewModel: TaskScreenViewModel,
    drawerState: DrawerUiState,
    onUpClicked: () -> Unit,
    onStageClicked: () -> Unit,
    onCategoryClicked: () -> Unit,
) {
    val taskState by taskScreenViewModel.taskState.collectAsStateWithLifecycle()

    LaunchedEffect(drawerState){
        val stage = drawerState.selectedStage
        val category = drawerState.selectedCategory

        if (stage != null) taskScreenViewModel.updateStage(stage)
        if (category != null) taskScreenViewModel.updateCategory(category)
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        item{
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillParentMaxSize()
            ) {
                BackButton(
                    size = 30.dp,
                    onClick = onUpClicked
                )

                val context = LocalContext.current
                Bar(
                    stage = taskState.stage,
                    category = taskState.category,
                    saveEnabled = !taskState.saving,
                    onStageClicked = onStageClicked,
                    onCategoryClicked = onCategoryClicked,
                    onSaveClicked = { taskScreenViewModel.saveTask(context) },
                    onCompleteClicked = { taskScreenViewModel.updateStage(Stage.COMPLETED_STAGE) }
                )

                Box(
                    contentAlignment = Alignment.CenterStart,
                ){
                    BasicTextField(
                        value = taskState.title,
                        onValueChange = taskScreenViewModel::updateTitle,
                        cursorBrush = SolidColor(AppColor.ButtonOutline),
                        textStyle = TextStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppColor.OnSurfaceAndBackground
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    this@Column.AnimatedVisibility(taskState.title.isEmpty()) {
                        Text(
                            text = stringResource(R.string.title),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppColor.OnSurfaceAndBackground
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    BasicTextField(
                        value = taskState.description,
                        onValueChange = taskScreenViewModel::updateDescription,
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = AppColor.OnSurfaceAndBackground
                        ),
                        cursorBrush = SolidColor(AppColor.ButtonOutline),
                        modifier = Modifier.matchParentSize()
                    )

                    this@Column.AnimatedVisibility(taskState.description.isEmpty()) {
                        Text(
                            text = "Description",
                            fontSize = 16.sp,
                            color = AppColor.OnSurfaceAndBackground
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun TaskScreenPreview() {
    val viewModel = TaskScreenViewModel(SavedStateHandle(), previewTaskRepository())
    val state = DrawerUiState()

    TaskScreen(
        taskScreenViewModel = viewModel,
        drawerState = state,
        onUpClicked = {},
        onStageClicked = {},
        onCategoryClicked = {}
    )
}