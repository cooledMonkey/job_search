package com.example.android.test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [VacancyEntity::class], version = 3, exportSchema = false
)
abstract class VacancyDatabase : RoomDatabase() {

    abstract val vacancyDatabaseDao: VacancyDao

    companion object {
        @Volatile
        private var INSTANCE: VacancyDatabase? = null

        fun getInstance(context: Context): VacancyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VacancyDatabase::class.java,
                        "vacancy_entity"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}