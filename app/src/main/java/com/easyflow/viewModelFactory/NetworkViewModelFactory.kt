package com.easyflow.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easyflow.repository.NetworkRepository
import com.easyflow.viewModel.NetworkViewModel

class NetworkViewModelFactory(private val networkRepository: NetworkRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NetworkViewModel(networkRepository) as T
    }

}