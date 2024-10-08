package com.example.android.test.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.test.R
import com.example.android.test.database.VacancyDao
import com.example.android.test.database.VacancyEntity
import com.example.android.test.network.Address
import com.example.android.test.network.Experience
import com.example.android.test.network.Offers
import com.example.android.test.network.Salary
import com.example.android.test.network.Vacancies
import com.example.android.test.network.VacanciesAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(val dataSource: VacancyDao) : ViewModel() {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    init {
        getResponseObject()
    }

    private var _vacancies = MutableLiveData<List<Vacancies>>()
    val vacancies: LiveData<List<Vacancies>>
        get() = _vacancies

    private var _filters = MutableLiveData<List<Offers>>()
    val filters: LiveData<List<Offers>>
        get() = _filters

    private var _isBigBlueButtonClicked = MutableLiveData(false)
    val isBigBlueButtonClicked: LiveData<Boolean>
        get() = _isBigBlueButtonClicked

    private fun getResponseObject() {
        viewModelScope.launch {
            if (_vacancies.value == null) {
                val resDeferred = VacanciesAPI.retrofitService.getInfo()
                try {
                    val res = resDeferred.await()
                    _vacancies.postValue(res.vacancies)
                    _filters.postValue(res.offers)
                    val list: List<VacancyEntity> = vacancyNetworkToDatabaseMap(res.vacancies)
                    dataSource.insertList(list)
                } catch (e: Exception) {
                    Log.e("NETWORK", e.toString())
                }
            } else {
                val newList = dataSource.getList()
                _vacancies.postValue(newList.let { vacancyDatabaseToNetworkMap(it ?: emptyList()) })
            }

        }
    }

    fun onBigBlueButtonClick() {
        _isBigBlueButtonClicked.value = true
        buildListVacancies().toList()
    }

    fun buildVacanciesCountString(context: Context): String {
        val res = when (_vacancies.value?.let { (it).size }) {
            1 -> context.getString(R.string.vacancy_1)
            2, 3, 4 -> context.getString(R.string.vacancy_2_4)
            else -> context.getString(R.string.vacancy_5_0)
        }
        return _vacancies.value?.let { (it).size }.toString() + " " + res
    }

    fun buildListVacancies(): MutableList<Any> {
        if (!_isBigBlueButtonClicked.value!!) {
            var newList: MutableList<Any> = _vacancies.value?.toMutableList()
                ?: emptyList<Any>().toMutableList()
            if ((_vacancies.value?.size ?: 0) > 3) {
                newList = newList.take(3) as MutableList<Any>
                newList.add(MoreButton(_vacancies.value?.size ?: 0))
            }
            return newList
        } else {
            return (_vacancies.value?.toMutableList()
                ?: emptyList<Any>().toMutableList().toMutableList()) as MutableList<Any>
        }
    }

    fun onFavouriteMarkClick(id: String) {
        for (i in 0..(_vacancies.value?.size?.minus(1) ?: 0)) {
            if (_vacancies.value?.get(i)?.id == id) {
                if (_vacancies.value?.get(i)?.isFavorite == true) {
                    _vacancies.value?.get(i)?.isFavorite = false
                } else {
                    _vacancies.value?.get(i)?.isFavorite = true
                }
            }
        }
        viewModelScope.launch {
            dataSource.updateList(vacancyNetworkToDatabaseMap(_vacancies.value
                ?: emptyList<Vacancies>()))
        }
    }

    fun vacancyNetworkToDatabaseMap(vacancies: List<Vacancies>): List<VacancyEntity> {
        var newList = emptyList<VacancyEntity>().toMutableList()
        for (vacancy in vacancies) {
            newList.add(
                VacancyEntity(
                    vacancy.id.toString(), vacancy.looking_number, vacancy.title,
                    vacancy.address.town, vacancy.address.street, vacancy.address.house,
                    vacancy.company, vacancy.experience.previewText, vacancy.experience.text,
                    vacancy.publishedDate, vacancy.isFavorite, vacancy.appliedNumber,
                    vacancy.description, vacancy.responsibilities
                )

            )
        }
        return newList.toList()
    }

    fun vacancyDatabaseToNetworkMap(vacancies: List<VacancyEntity>): List<Vacancies> {
        var newList = emptyList<Vacancies>().toMutableList()
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
        return newList.toList()
    }

    fun updateVacanciesList(){
        getResponseObject()
    }
}
