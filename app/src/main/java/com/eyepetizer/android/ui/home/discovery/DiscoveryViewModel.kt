package com.eyepetizer.android.ui.home.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eyepetizer.android.logic.MainPageRepository
import com.eyepetizer.android.logic.model.Discovery
import kotlinx.coroutines.flow.Flow

class DiscoveryViewModel(val repository: MainPageRepository) : ViewModel() {

    var dataList = ArrayList<Discovery.Item>()

    fun getPagingData(): Flow<PagingData<Discovery.Item>> {
        return repository.getDiscoveryPagingData().cachedIn(viewModelScope)
    }
}