package com.mariacarvalho.studytrack.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mariacarvalho.studytrack.data.local.SubjectDao

@Database(
    entities = [
        SubjectEntity::class,
        StudySessionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class StudyTrackDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao

    abstract fun studySessionDao(): StudySessionDao

    companion object {
        @Volatile
        private var INSTANCE: StudyTrackDatabase? = null

        fun getDatabase(context: Context): StudyTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyTrackDatabase::class.java,
                    "studytrack_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}