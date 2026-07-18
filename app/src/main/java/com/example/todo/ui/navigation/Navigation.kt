package com.example.todo.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.di.AppViewModelProvider
import com.example.todo.ui.drawer.DrawerUiState
import com.example.todo.ui.homescreen.HomeScreen
import com.example.todo.ui.homescreen.HomeScreenViewModel
import com.example.todo.ui.taskscreen.TaskScreen
import com.example.todo.ui.taskscreen.TaskScreenViewModel

@Composable
fun ToDoNavigation(
    drawerState: DrawerUiState,
    resetSelection: () -> Unit,
    onStageClicked: () -> Unit,
    onCategoryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = AppRoute.HomeScreen,
        enterTransition = { slideInHorizontally{ it } },
        exitTransition = { slideOutHorizontally{ -it } },
        popEnterTransition = { slideInHorizontally{ -it } },
        popExitTransition = { slideOutHorizontally{ it } },
        modifier = modifier
    ){
        composable<AppRoute.HomeScreen>{
            val homeScreenViewModel = viewModel<HomeScreenViewModel>(
                factory = AppViewModelProvider.homeScreenViewModelFactory()
            )

            HomeScreen(
                viewModel = homeScreenViewModel,
                drawerState = drawerState,
                resetSelection = resetSelection,
                onStageClicked = onStageClicked,
                onCategoryClicked = onCategoryClicked,
                onTaskClicked = { controller.navigate(AppRoute.TaskScreen(it.id)) },
                onFABClick = { controller.navigate(AppRoute.TaskScreen(null)) }
            )
        }

        composable<AppRoute.TaskScreen> {
            val taskScreenViewModel = viewModel<TaskScreenViewModel>(
                factory = AppViewModelProvider.taskScreenViewModelFactory()
            )

            TaskScreen(
                taskScreenViewModel = taskScreenViewModel,
                drawerState = drawerState,
                onUpClicked = controller::navigateUp,
                onStageClicked = onStageClicked,
                onCategoryClicked = onCategoryClicked
            )
        }
    }
}