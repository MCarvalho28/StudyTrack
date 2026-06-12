package com.mariacarvalho.studytrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariacarvalho.studytrack.data.local.StudySessionEntity
import com.mariacarvalho.studytrack.data.local.SubjectEntity
import com.mariacarvalho.studytrack.data.repository.StudyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudyViewModel(
    private val repository: StudyRepository
) : ViewModel() {

    val subjects: Flow<List<SubjectEntity>> = repository.getAllSubjects()

    val allSessions: Flow<List<StudySessionEntity>> = repository.getAllSessions()

    val totalStudyTime: Flow<Int?> = repository.getTotalStudyTime()

    private val _selectedSubject = MutableStateFlow<SubjectEntity?>(null)
    val selectedSubject: StateFlow<SubjectEntity?> = _selectedSubject

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun addSubject(name: String, color: String = "#A78BFA") {
        if (name.isBlank()) {
            _errorMessage.value = "O nome da disciplina não pode estar vazio."
            return
        }

        viewModelScope.launch {
            try {
                repository.insertSubject(
                    SubjectEntity(
                        name = name.trim(),
                        color = color
                    )
                )
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao adicionar disciplina."
            }
        }
    }

    fun loadSubjectById(subjectId: Int) {
        viewModelScope.launch {
            try {
                _selectedSubject.value = repository.getSubjectById(subjectId)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar disciplina."
            }
        }
    }

    fun updateSubject(id: Int, name: String, color: String = "#3F51B5") {
        if (name.isBlank()) {
            _errorMessage.value = "O nome da disciplina não pode estar vazio."
            return
        }

        viewModelScope.launch {
            try {
                repository.updateSubject(
                    SubjectEntity(
                        id = id,
                        name = name.trim(),
                        color = color
                    )
                )
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao atualizar disciplina."
            }
        }
    }

    fun deleteSubject(subject: SubjectEntity) {
        viewModelScope.launch {
            try {
                repository.deleteSubject(subject)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao apagar disciplina."
            }
        }
    }

    fun getSessionsBySubject(subjectId: Int): Flow<List<StudySessionEntity>> {
        return repository.getSessionsBySubject(subjectId)
    }

    fun getTotalStudyTime(subjectId: Int): Flow<Int?> {
        return repository.getTotalStudyTime(subjectId)
    }

    fun addStudySession(subjectId: Int, durationText: String, note: String) {
        val duration = durationText.toIntOrNull()

        if (duration == null || duration <= 0) {
            _errorMessage.value = "A duração deve ser maior que zero."
            return
        }

        viewModelScope.launch {
            try {
                repository.insertSession(
                    StudySessionEntity(
                        subjectId = subjectId,
                        durationMinutes = duration,
                        note = note.trim()
                    )
                )
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao adicionar sessão de estudo."
            }
        }
    }

    fun deleteSessionById(sessionId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteSessionById(sessionId)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao apagar sessão de estudo."
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}