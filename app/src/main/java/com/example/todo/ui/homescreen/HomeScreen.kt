package com.example.todo.ui.homescreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todo.R
import com.example.todo.domain.model.Task
import com.example.todo.ui.componenets.ThemedDialog
import com.example.todo.ui.drawer.DrawerUiState
import com.example.todo.ui.drawer.DrawerViewModel
import com.example.todo.ui.homescreen.components.ButtonRow
import com.example.todo.ui.homescreen.components.FloatingActionButton
import com.example.todo.ui.homescreen.components.TaskListItem
import com.example.todo.ui.homescreen.components.ThemedSearchBar
import com.example.todo.ui.theme.AppColor
import com.example.todo.ui.util.previewTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    drawerState: DrawerUiState,
    resetSelection: () -> Unit,
    onStageClicked: () -> Unit,
    onCategoryClicked: () -> Unit,
    onTaskClicked: (Task) -> Unit,
    onFABClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()
    val tasks by viewModel.filteredTasks.collectAsStateWithLifecycle()

    BackHandler(uiState.isSelecting) {
        viewModel.exitSelectingMode()
    }

    LaunchedEffect(
        drawerState.selectedStage,
        drawerState.selectedCategory,
        drawerState.removalType
    ) {
        val category = drawerState.selectedCategory
        val stage = drawerState.selectedStage

        if(category != null) viewModel.setCategoryFilter(category)
        if(stage != null) viewModel.setStageFilter(stage)

        launch(Dispatchers.Default){
            searchState.stage?.let {
                if (it !in drawerState.stages) viewModel.resetStageFilter()
            }

            searchState.category?.let {
                if (it !in drawerState.categories) viewModel.resetCategoryFilter()
            }
        }

        resetSelection()
    }

    Scaffold(
        containerColor = AppColor.Background,
        floatingActionButton = {
            FloatingActionButton(onClick = onFABClick)
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            ThemedSearchBar(
                query = searchState.query,
                isDropDownMenuExpanded = uiState.isDropDownExpanded,
                categories = viewModel.CATEGORIES,
                onDismiss = viewModel::closeDropDownMenu,
                onQueryChange = viewModel::updateSearchQuery,
                onSearch = viewModel::updateSearchQuery,
                onSearchOptionClick = {
                    if(uiState.isDropDownExpanded) viewModel.closeDropDownMenu()
                    else viewModel.openDropDownMenu()
                }
            )

            ButtonRow(
                stage = searchState.stage,
                category = searchState.category,
                onStageClicked = onStageClicked,
                onCategoryClicked = onCategoryClicked,
                onDeleteClicked = {
                    if (uiState.taskSelected.isNotEmpty()) {
                        viewModel.showDialogBox()
                    }
                },
                onRemoveFilterClicked = {
                    viewModel.resetStageFilter()
                    viewModel.resetCategoryFilter()
                }
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                items(tasks, key = Task::id){
                    val isSelected = it.id in uiState.taskSelected

                    TaskListItem(
                        task = it,
                        isSelected = isSelected,
                        inSelectMode = uiState.isSelecting,
                        onActionButtonClicked = viewModel::completeTask,
                        onItemClicked = { task ->
                            if (uiState.isSelecting) {
                                if (isSelected) {
                                    viewModel.removeFromSelectedTask(task)
                                } else {
                                    viewModel.addToSelectedTask(task)
                                }
                            } else onTaskClicked(it)
                        },
                        onLongPress = { task ->
                            viewModel.enterSelectMode()
                            viewModel.addToSelectedTask(task)
                        }
                    )
                }
            }
        }

        if(uiState.showingDialog){
            ThemedDialog(
                message = stringResource(R.string.do_you_want_to_delete_tasks),
                onYes = {
                    viewModel.deleteTasks()
                    viewModel.dismissDialogBox()
                },
                onNo = {
                    viewModel.exitSelectingMode()
                    viewModel.dismissDialogBox()
                },
                onDismiss = viewModel::dismissDialogBox
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun HomeScreenPreview() {
    val repo = previewTaskRepository()

    val viewModel = HomeScreenViewModel(repo)
    val drawerViewModel = DrawerViewModel(repo)
    val drawerState by drawerViewModel.uiState.collectAsState()

    HomeScreen(
        viewModel = viewModel,
        drawerState = drawerState,
        resetSelection = { },
        onStageClicked = { },
        onCategoryClicked = { },
        onTaskClicked = { },
        onFABClick = { }
    )
}