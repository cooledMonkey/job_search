package com.example.android.test.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



data class ResponseObject(
    val offers: List<Offers>,
    val vacancies: List<Vacancies>
)


data class Offers(
    val id: String?,
    val title: String,
    val button: Button?,
    val link: String
)


data class Button(
    val text: String
)


data class Vacancies(
    val id: String,
    val looking_number: Int?,
    val title: String,
    val address: Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    var isFavorite: Boolean,
    val salary: Salary,
    val schedules: List<String>,
    val appliedNumber: Int?,
    val description: String?,
    val responsibilities: String,
    val questions: List<String>
)

data class Address(
    val town: String,
    val street: String,
    val house: String
)


data class Experience(
    val previewText: String,
    val text: String
)


data class Salary(
    val short: String?,
    val full: String?
)

