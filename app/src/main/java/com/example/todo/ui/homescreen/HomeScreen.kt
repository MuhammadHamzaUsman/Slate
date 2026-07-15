package com.example.todo.ui.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.data.model.Category
import com.example.todo.data.model.Stage
import com.example.todo.di.AppViewModelProvider
import com.example.todo.domain.model.Task
import com.example.todo.domain.repository.TaskRepository
import com.example.todo.ui.drawer.DrawerViewModel
import com.example.todo.ui.theme.AppColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.homeScreenViewModelFactory()),
    drawerViewModel: DrawerViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()
    val drawerState by drawerViewModel.uiState.collectAsStateWithLifecycle()
    val tasks by viewModel.filteredTasks.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = AppColor.Background
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .safeDrawingPadding()
                .padding(16.dp)
        ) {
            ThemedSearchBar(
                query = searchState.query,
                onQueryChange = viewModel::updateSearchQuery,
                onSearch = viewModel::updateSearchQuery
            )

            ButtonRow(
                onStageClicked = drawerViewModel::openDrawer,
                onCategoryClicked = drawerViewModel::openDrawer,
                onDeleteClicked = viewModel::deleteTasks
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
                        onItemClicked = {
                            if (uiState.isSelecting) viewModel.addToSelectedTask(it)
                            else {}
                        },
                        onLongPress = {
                            viewModel.enterSelectMode()
                            viewModel.addToSelectedTask(it)
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
    val repos = object : TaskRepository {
        override fun getTasks(): Flow<List<Task>> = flowOf()
        override fun getFilteredTasks(searchState: SearchState): Flow<List<Task>> = flowOf()
        override fun getTasksByCategory(category: Category): Flow<List<Task>> = flowOf()
        override fun getTasksByStage(stage: Stage): Flow<List<Task>> = flowOf()
        override fun getTask(id: Int): Flow<Task?> = flowOf()
        override suspend fun insertTask(task: Task): Int = 0
        override suspend fun updateTask(task: Task) {}
        override suspend fun deleteTask(taskIds: Set<Int>) {}
        override fun getCategories(): Flow<List<Category>> = flowOf()
        override suspend fun addCategory(category: Category) {}
        override suspend fun removeCategory(category: Category) {}
        override fun getStages(): Flow<List<Stage>> = flowOf()
        override suspend fun addStage(stage: Stage) {}
        override suspend fun removeStage(stage: Stage) {}
    }

    val viewModel = HomeScreenViewModel(repos)
    val drawerViewModel = DrawerViewModel(repos)

    HomeScreen(viewModel = viewModel, drawerViewModel = drawerViewModel)
}