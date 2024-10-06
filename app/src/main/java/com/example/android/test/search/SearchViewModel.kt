package com.example.android.test.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.test.network.ResponseObject
import com.example.android.test.network.Vacancies
import com.example.android.test.network.VacanciesAPI
import com.example.android.test.network.VacanciesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchViewModel() : ViewModel() {
    val viewModelJob = Job()
    val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    init {
        getResponseObject()
    }

    private var _response = MutableLiveData<ResponseObject>()
    private var _vacancies = MutableLiveData<List<Vacancies>>()
    val vacancies: LiveData<List<Vacancies>>
        get() = _vacancies

    private fun getResponseObject() {
            viewModelScope.launch {
                val resDeferred = VacanciesAPI.retrofitService.getInfo()
                try {
                    _vacancies.postValue(resDeferred.await().vacancies)
                    //Log.i("NETWORK", resDeferred.await().vacancies.toString())
                } catch (e: Exception) {
                    Log.e("NETWORK", e.toString())
                }

        }
    }

    private fun extractVacancies(){
        _vacancies.value = _response.value?.vacancies
    }
}
