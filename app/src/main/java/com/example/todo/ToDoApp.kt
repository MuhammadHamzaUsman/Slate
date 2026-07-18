package com.example.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.di.AppViewModelProvider
import com.example.todo.ui.drawer.ActionType
import com.example.todo.ui.drawer.Drawer
import com.example.todo.ui.drawer.DrawerViewModel
import com.example.todo.ui.drawer.RemovalType
import com.example.todo.ui.navigation.ToDoNavigation
import com.example.todo.ui.theme.AppColor
import kotlinx.coroutines.launch

@Composable
fun ToDoApp(modifier: Modifier = Modifier) {
    val drawerViewModel: DrawerViewModel = viewModel(factory = AppViewModelProvider.drawerViewModelFactory())
    val drawerUiState by drawerViewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = modifier
            .background(AppColor.Background)
            .safeDrawingPadding(),
        drawerState = drawerState,
        drawerContent = {
            Drawer(
                drawerState = drawerUiState,

                onCategoryPlus = { drawerViewModel.showDialog(ActionType.ADD_CATEGORY) },
                onCategoryRemove = {
                    if(drawerUiState.removalType == RemovalType.NONE) drawerViewModel.enterRemovingMode(RemovalType.REMOVE_CATEGORY)
                    else drawerViewModel.exitRemovingMode()
                },
                onCategoryItemClicked = {
                    if(drawerUiState.removalType == RemovalType.REMOVE_CATEGORY) drawerViewModel.showDialog(ActionType.REMOVE_CATEGORY(it))
                    else {
                        drawerViewModel.selectCategory(it)
                        coroutineScope.launch { drawerState.close() }
                    }
                },

                onStagePlus = { drawerViewModel.showDialog(ActionType.ADD_STAGE) },
                onStageRemove = {
                    if(drawerUiState.removalType == RemovalType.NONE) drawerViewModel.enterRemovingMode(RemovalType.REMOVE_STAGE)
                    else drawerViewModel.exitRemovingMode()
                },
                onStageItemClicked = {
                    if(drawerUiState.removalType == RemovalType.REMOVE_STAGE) drawerViewModel.showDialog(ActionType.REMOVE_STAGE(it))
                    else {
                        drawerViewModel.selectStage(it)
                        coroutineScope.launch { drawerState.close() }
                    }
                },

                onDialogSaveCategory = { name, color ->
                    drawerViewModel.addCategory(name, color)
                    drawerViewModel.dismissDialog()
                },
                onDialogDeleteCategory = {
                    coroutineScope.launch{
                        drawerViewModel.removeCategory(it)
                        drawerViewModel.dismissDialog()
                    }
                },
                onDialogSaveStage = { name, color ->
                    drawerViewModel.addStage(name, color)
                    drawerViewModel.dismissDialog()
                },
                onDialogDeleteStage = {
                    coroutineScope.launch{
                        drawerViewModel.removeStage(it)
                        drawerViewModel.dismissDialog()
                    }
                },
                onDialogDismiss = drawerViewModel::dismissDialog,
                onUpClicked = {
                    coroutineScope.launch {
                        drawerState.close()
                        drawerViewModel.exitRemovingMode()
                    }
                },
                modifier = Modifier.fillMaxWidth(0.9f)
            )
        }
    ) {
        ToDoNavigation(
            drawerState = drawerUiState,
            resetSelection = drawerViewModel::resetSelection,
            onStageClicked = { coroutineScope.launch{ drawerState.open() } },
            onCategoryClicked = { coroutineScope.launch{ drawerState.open() } }
        )
    }
}