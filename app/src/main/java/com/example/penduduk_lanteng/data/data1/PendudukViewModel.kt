package com.example.penduduk_lanteng.data.data1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.penduduk_lanteng.DB.entity.Penduduk
import com.example.penduduk_lanteng.DB.repository.PendudukRepository

class PendudukViewModel(private val repository: PendudukRepository) : ViewModel() {

    fun getPendudukByRT(rt: String): LiveData<List<Penduduk>> {
        return repository.getPendudukByRT(rt)
    }
}

class PendudukViewModelFactory(private val repository: PendudukRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PendudukViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PendudukViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
