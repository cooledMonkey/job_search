package com.example.android.test.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.test.database.VacancyDao

class SearchViewModelFactory(private val database: VacancyDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(database) as T
    }
}
