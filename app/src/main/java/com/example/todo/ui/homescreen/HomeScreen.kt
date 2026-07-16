package com.example.todo.ui.homescreen

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.di.AppViewModelProvider
import com.example.todo.domain.model.Task
import com.example.todo.ui.drawer.DrawerUiState
import com.example.todo.ui.drawer.DrawerViewModel
import com.example.todo.ui.theme.AppColor
import com.example.todo.ui.util.previewTaskRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.homeScreenViewModelFactory()),
    drawerState: DrawerUiState,
    resetSelection: () -> Unit,
    onStageClicked: () -> Unit,
    onCategoryClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()
    val tasks by viewModel.filteredTasks.collectAsStateWithLifecycle()

    LaunchedEffect(drawerState) {
        val category = drawerState.selectedCategory
        val stage = drawerState.selectedStage

        if(category != null) viewModel.setCategoryFilter(category)
        if(stage != null) viewModel.setStageFilter(stage)

        resetSelection()
    }

    Scaffold(
        containerColor = AppColor.Background,
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
                onQueryChange = viewModel::updateSearchQuery,
                onSearch = viewModel::updateSearchQuery
            )

            ButtonRow(
                stage = searchState.stage,
                category = searchState.category,
                onStageClicked = onStageClicked,
                onCategoryClicked = onCategoryClicked,
                onDeleteClicked = viewModel::deleteTasks,
                onRemoveFilterClicked = {
                    Log.d("Filter", "Clcked")
                    viewModel.resetStageFilter()
                    viewModel.resetCategoryFilter()
                }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                items(tasks, key = Task::id){
                    TaskListItem(
                        task = it,
                        isSelected = it.id in uiState.taskSelected,
                        onActionButtonClicked = viewModel::completeTask,
                        onItemClicked = { task ->
                            if (uiState.isSelecting) viewModel.addToSelectedTask(task)
                            else {
                                // TODO
                            }
                        },
                        onLongPress = { task ->
                            viewModel.enterSelectMode()
                            viewModel.addToSelectedTask(task)
                        }
                    )
                }
            }
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
        onCategoryClicked = { }
    )
}