package com.mariacarvalho.studytrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mariacarvalho.studytrack.ui.screens.AddSubjectScreen
import com.mariacarvalho.studytrack.ui.screens.HomeScreen
import com.mariacarvalho.studytrack.viewmodel.StudyViewModel

@Composable
fun AppNavigation(
    viewModel: StudyViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onAddSubjectClick = {
                    navController.navigate(Screen.AddSubject.route)
                }
            )
        }

        composable(Screen.AddSubject.route) {
            AddSubjectScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}