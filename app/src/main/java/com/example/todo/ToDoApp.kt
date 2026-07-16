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
import com.example.todo.ui.homescreen.HomeScreen
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

                onCategoryPlus = { drawerViewModel.showDialog(ActionType.CATEGORY) },
                onCategoryRemove = {
                    if(drawerUiState.removing == ActionType.NONE) drawerViewModel.enterRemovingMode(ActionType.CATEGORY)
                    else drawerViewModel.exitRemovingMode()
                },
                onCategoryItemClicked = {
                    if(drawerUiState.removing == ActionType.CATEGORY) drawerViewModel.removeCategory(it)
                    else {
                        drawerViewModel.selectCategory(it)
                        coroutineScope.launch { drawerState.close() }
                    }
                },

                onStagePlus = { drawerViewModel.showDialog(ActionType.STAGE) },
                onStageRemove = {
                    if(drawerUiState.removing == ActionType.NONE) drawerViewModel.enterRemovingMode(ActionType.STAGE)
                    else drawerViewModel.exitRemovingMode()
                },
                onStageItemClicked = {
                    if(drawerUiState.removing == ActionType.STAGE) drawerViewModel.removeStage(it)
                    else {
                        drawerViewModel.selectStage(it)
                        coroutineScope.launch { drawerState.close() }
                    }
                },

                onDialogSaveCategory = { name, color ->
                    drawerViewModel.addCategory(name, color)
                    drawerViewModel.dismissDialog()
                },
                onDialogSaveStage = { name, color ->
                    drawerViewModel.addStage(name, color)
                    drawerViewModel.dismissDialog()
                },
                onDialogDismiss = drawerViewModel::dismissDialog,
                onUpClicked = { coroutineScope.launch { drawerState.close() } },
                onBack = { coroutineScope.launch { drawerState.close() } },
                modifier = Modifier.fillMaxWidth(0.9f)
            )
        }
    ) {
        HomeScreen(
            drawerState = drawerUiState,
            resetSelection = drawerViewModel::resetSelection,
            onStageClicked = { coroutineScope.launch { drawerState.open() } },
            onCategoryClicked = { coroutineScope.launch { drawerState.open() } }
        )
    }
}