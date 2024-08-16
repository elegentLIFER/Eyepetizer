package com.eyepetizer.android.ui.home.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eyepetizer.android.logic.MainPageRepository

class DailyViewModelFactory(private val repository: MainPageRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DailyViewModel(repository) as T
    }
}