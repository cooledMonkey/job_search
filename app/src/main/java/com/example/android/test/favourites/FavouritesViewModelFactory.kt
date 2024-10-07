package com.example.android.test.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.test.database.VacancyDao

class FavouritesViewModelFactory(private val database: VacancyDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouritesViewModel(database) as T
    }
}