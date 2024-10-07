package com.example.android.test.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("vacancy_entity")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("looking_number")
    val lookingNumber: Int?,
    val title: String,
    val town: String,
    val street: String,
    val house: String,
    val company: String,
    @ColumnInfo("preview_text")
    val previewText: String,
    val text: String,
    @ColumnInfo("published_date")
    val publishedDate: String,
    @ColumnInfo("is_favourite")
    var isFavorite: Boolean,
    @ColumnInfo("applied_number")
    val appliedNumber: Int?,
    val description: String?,
    val responsibilities: String
)
