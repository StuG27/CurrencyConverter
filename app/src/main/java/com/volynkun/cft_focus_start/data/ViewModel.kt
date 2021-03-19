package com.volynkun.cft_focus_start.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ViewModel : ViewModel() {

    private val repository = Repository
    private val listLiveData = MutableLiveData<MutableList<Currency>>(mutableListOf())
    private val isLoadingLiveData = MutableLiveData(false)

    val list: LiveData<MutableList<Currency>>
        get() = listLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun updateJson(savedInstance: String?) {
        repository.updateJson(savedInstance)
    }

    fun getCurrencies(): MutableList<Currency> {
        return Repository.getCurrencies()
    }

    fun getMyJson(): String? {
        return Repository.getMyJson()
    }

    fun search(isManual: Boolean) {
        isLoadingLiveData.postValue(true)
        repository.searchJson(
            isManual,
            onComplete = { currencies ->
                isLoadingLiveData.postValue(false)
                listLiveData.postValue(currencies)
            },
            onError = {
                isLoadingLiveData.postValue(false)
                listLiveData.postValue(mutableListOf())
            }
        )
    }
}