package com.mariacarvalho.studytrack.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddSubject : Screen("add_subject")
    object SubjectDetail : Screen("subject_detail/{subjectId}") {
        fun createRoute(subjectId: Int): String {
            return "subject_detail/$subjectId"
        }
    }

    object AddStudySession : Screen("add_study_session/{subjectId}") {
        fun createRoute(subjectId: Int): String {
            return "add_study_session/$subjectId"
        }
    }

    object EditSubject : Screen("edit_subject/{subjectId}") {
        fun createRoute(subjectId: Int): String {
            return "edit_subject/$subjectId"
        }
    }
}