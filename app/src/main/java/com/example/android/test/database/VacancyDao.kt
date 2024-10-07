package com.example.android.test.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<VacancyEntity>)

    @Update
    fun updateList(list: List<VacancyEntity>)

    @Query("select * from vacancy_entity")
    fun getList(): List<VacancyEntity>
}