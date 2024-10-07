package com.example.android.test.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.test.database.VacancyDao
import com.example.android.test.database.VacancyEntity
import com.example.android.test.network.Address
import com.example.android.test.network.Experience
import com.example.android.test.network.Salary
import com.example.android.test.network.Vacancies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavouritesViewModel(val dataSource: VacancyDao) : ViewModel() {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)


    private var _vacancies = MutableLiveData<List<Vacancies>>()
    val vacancies: LiveData<List<Vacancies>>
        get() = _vacancies

    init {
        getResponseObject()
    }

    private fun getResponseObject() {
        viewModelScope.launch {
            val newList = dataSource.getList()
            _vacancies.postValue(vacancyDatabaseToNetworkMap(newList))
        }
    }

    fun updateVacanciesList(){
        getResponseObject()
    }

    fun filterVacancies(list: List<Vacancies>): List<Vacancies> {
        val newList: MutableList<Vacancies> = emptyList<Vacancies>().toMutableList()
        for (i in list) {
            if (i.isFavorite == true) {
                newList.add(i)
            }
        }
        return newList
    }

    fun vacancyNetworkToDatabaseMap(vacancies: List<Vacancies>): MutableList<VacancyEntity> {
        val newList = emptyList<VacancyEntity>().toMutableList()
        for (vacancy in vacancies) {
            newList.add(
                VacancyEntity(
                    vacancy.id, vacancy.looking_number, vacancy.title,
                    vacancy.address.town, vacancy.address.street, vacancy.address.house,
                    vacancy.company, vacancy.experience.previewText, vacancy.experience.text,
                    vacancy.publishedDate, vacancy.isFavorite, vacancy.appliedNumber,
                    vacancy.description, vacancy.responsibilities
                )

            )
        }
        return newList
    }

    fun vacancyDatabaseToNetworkMap(vacancies: List<VacancyEntity>?): List<Vacancies> {
        val newList = emptyList<Vacancies>().toMutableList()
        if (vacancies != null) {
            for (vacancy in vacancies) {
                newList.add(
                    Vacancies(
                        vacancy.id, vacancy.lookingNumber,
                        vacancy.title, Address(vacancy.town, vacancy.street, vacancy.house),
                        vacancy.company, Experience(vacancy.previewText, vacancy.text),
                        vacancy.publishedDate, vacancy.isFavorite,
                        Salary(null, null), emptyList(),
                        vacancy.appliedNumber, vacancy.description,
                        vacancy.responsibilities, emptyList()
                    )
                )
            }
        }
        return newList
    }

    fun onFavouriteMarkClick(id: String) {
        for (i in 0..(_vacancies.value?.size?.minus(1) ?: 0)) {
            if (_vacancies.value?.get(i)?.id == id) {
                if(_vacancies.value?.get(i)?.isFavorite == true) {
                    _vacancies.value?.get(i)?.isFavorite = false
                } else {
                    _vacancies.value?.get(i)?.isFavorite = true
                }
            }
        }
        viewModelScope.launch {
            _vacancies.value?.let { vacancyNetworkToDatabaseMap(it) }
                ?.let { dataSource.insertList(it) }
            val newList = dataSource.getList()
            _vacancies.postValue(vacancyDatabaseToNetworkMap(newList))
        }
    }
}