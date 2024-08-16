package com.eyepetizer.android.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.eyepetizer.android.logic.MainPageRepository

class SearchViewModel(val repository: MainPageRepository) : ViewModel() {

    var dataList = ArrayList<String>()

    private var requestParamLiveData = MutableLiveData<Any>()

    val dataListLiveData = Transformations.switchMap(requestParamLiveData) {
        liveData {
            val resutlt = try {
                val hotSearch = repository.refreshHotSearch()
                Result.success(hotSearch)
            } catch (e: Exception) {
                Result.failure<List<String>>(e)
            }
            emit(resutlt)
        }
    }

    fun onRefresh() {
        requestParamLiveData.value = requestParamLiveData.value
    }
}