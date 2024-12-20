package com.example.statussaver.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.statussaver.data.StatusRepo
import com.example.statussaver.viewmodel.StatusViewModel

class StatusViewModelFactory(private val repo:StatusRepo):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatusViewModel(repo) as T
    }


}