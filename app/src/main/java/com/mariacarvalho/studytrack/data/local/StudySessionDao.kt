package com.mariacarvalho.studytrack.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StudySessionDao {

    @Query("SELECT * FROM study_sessions WHERE subjectId = :subjectId ORDER BY date DESC")
    fun getSessionsBySubject(subjectId: Int): Flow<List<StudySessionEntity>>

    @Query("SELECT SUM(durationMinutes) FROM study_sessions WHERE subjectId = :subjectId")
    fun getTotalStudyTime(subjectId: Int): Flow<Int?>

    @Insert
    suspend fun insertSession(session: StudySessionEntity)

    @Update
    suspend fun updateSession(session: StudySessionEntity)

    @Delete
    suspend fun deleteSession(session: StudySessionEntity)

    @Query("DELETE FROM study_sessions WHERE id = :sessionId")
    suspend fun deleteSessionById(sessionId: Int)
}