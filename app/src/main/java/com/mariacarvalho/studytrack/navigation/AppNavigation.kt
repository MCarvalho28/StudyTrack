package com.mariacarvalho.studytrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mariacarvalho.studytrack.ui.screens.AddStudySessionScreen
import com.mariacarvalho.studytrack.ui.screens.AddSubjectScreen
import com.mariacarvalho.studytrack.ui.screens.HomeScreen
import com.mariacarvalho.studytrack.ui.screens.SubjectDetailScreen
import com.mariacarvalho.studytrack.viewmodel.StudyViewModel
import com.mariacarvalho.studytrack.ui.screens.EditSubjectScreen

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
                },
                onSubjectClick = { subjectId ->
                    navController.navigate(Screen.SubjectDetail.createRoute(subjectId))
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
        composable(
            route = Screen.SubjectDetail.route,
            arguments = listOf(
                navArgument("subjectId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getInt("subjectId") ?: 0

            SubjectDetailScreen(
                subjectId = subjectId,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddSessionClick = {
                    navController.navigate(Screen.AddStudySession.createRoute(subjectId))
                },
                onEditSubjectClick = {
                    navController.navigate(Screen.EditSubject.createRoute(subjectId))
                }
            )
        }

        composable(
            route = Screen.AddStudySession.route,
            arguments = listOf(
                navArgument("subjectId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getInt("subjectId") ?: 0

            AddStudySessionScreen(
                subjectId = subjectId,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.EditSubject.route,
            arguments = listOf(
                navArgument("subjectId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getInt("subjectId") ?: 0

            EditSubjectScreen(
                subjectId = subjectId,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}