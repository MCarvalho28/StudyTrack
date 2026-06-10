package com.mariacarvalho.studytrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mariacarvalho.studytrack.data.repository.StudyRepository

class StudyViewModelFactory(
    private val repository: StudyRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudyViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}