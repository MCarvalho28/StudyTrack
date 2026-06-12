package com.mariacarvalho.studytrack.data.repository

import com.mariacarvalho.studytrack.data.local.StudySessionDao
import com.mariacarvalho.studytrack.data.local.StudySessionEntity
import com.mariacarvalho.studytrack.data.local.SubjectDao
import com.mariacarvalho.studytrack.data.local.SubjectEntity
import kotlinx.coroutines.flow.Flow

class StudyRepository(
    private val subjectDao: SubjectDao,
    private val studySessionDao: StudySessionDao
) {

    fun getAllSubjects(): Flow<List<SubjectEntity>> {
        return subjectDao.getAllSubjects()
    }

    suspend fun getSubjectById(subjectId: Int): SubjectEntity? {
        return subjectDao.getSubjectById(subjectId)
    }

    suspend fun insertSubject(subject: SubjectEntity) {
        subjectDao.insertSubject(subject)
    }

    suspend fun updateSubject(subject: SubjectEntity) {
        subjectDao.updateSubject(subject)
    }

    suspend fun deleteSubject(subject: SubjectEntity) {
        subjectDao.deleteSubject(subject)
    }

    fun getAllSessions(): Flow<List<StudySessionEntity>> {
        return studySessionDao.getAllSessions()
    }

    fun getTotalStudyTime(): Flow<Int?> {
        return studySessionDao.getTotalStudyTime()
    }

    fun getSessionsBySubject(subjectId: Int): Flow<List<StudySessionEntity>> {
        return studySessionDao.getSessionsBySubject(subjectId)
    }

    fun getTotalStudyTime(subjectId: Int): Flow<Int?> {
        return studySessionDao.getTotalStudyTime(subjectId)
    }

    suspend fun insertSession(session: StudySessionEntity) {
        studySessionDao.insertSession(session)
    }

    suspend fun updateSession(session: StudySessionEntity) {
        studySessionDao.updateSession(session)
    }

    suspend fun deleteSession(session: StudySessionEntity) {
        studySessionDao.deleteSession(session)
    }

    suspend fun deleteSessionById(sessionId: Int) {
        studySessionDao.deleteSessionById(sessionId)
    }
}