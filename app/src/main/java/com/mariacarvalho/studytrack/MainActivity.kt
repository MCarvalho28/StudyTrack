package com.mariacarvalho.studytrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariacarvalho.studytrack.data.local.StudyTrackDatabase
import com.mariacarvalho.studytrack.data.repository.StudyRepository
import com.mariacarvalho.studytrack.navigation.AppNavigation
import com.mariacarvalho.studytrack.ui.theme.StudyTrackTheme
import com.mariacarvalho.studytrack.viewmodel.StudyViewModel
import com.mariacarvalho.studytrack.viewmodel.StudyViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = StudyTrackDatabase.getDatabase(applicationContext)

        val repository = StudyRepository(
            subjectDao = database.subjectDao(),
            studySessionDao = database.studySessionDao()
        )

        val factory = StudyViewModelFactory(repository)

        setContent {
            StudyTrackTheme {
                val studyViewModel: StudyViewModel = viewModel(
                    factory = factory
                )

                AppNavigation(
                    viewModel = studyViewModel
                )
            }
        }
    }
}