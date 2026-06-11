package com.mariacarvalho.studytrack.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddSubject : Screen("add_subject")
}