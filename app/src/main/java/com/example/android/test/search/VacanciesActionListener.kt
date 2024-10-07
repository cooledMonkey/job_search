package com.example.android.test.search

interface ButtonActionListener {
    fun onButtonClick()
}

interface VacanciesActionListener{
    fun onButtonClick()
    fun onFavouriteMarkClick(id: String)
    fun onVacancyCardClick()
}