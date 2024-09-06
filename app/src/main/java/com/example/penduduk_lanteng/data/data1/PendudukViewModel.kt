package com.example.penduduk_lanteng.data.data1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.penduduk_lanteng.DB.entity.Penduduk
import com.example.penduduk_lanteng.DB.repository.PendudukRepository
import kotlinx.coroutines.launch

class PendudukViewModel(private val repository: PendudukRepository) : ViewModel() {

    fun getPendudukByRT(rt: String): LiveData<List<Penduduk>> {
        return repository.getPendudukByRT(rt)
    }

    fun updatePenduduk(penduduk: Penduduk) {
        viewModelScope.launch {
            repository.updatePenduduk(penduduk)
        }
    }

    // Fungsi untuk mendapatkan penduduk berdasarkan ID
    fun getPendudukById(id: Int): LiveData<Penduduk> {
        return repository.getPendudukById(id)
    }

    // Fungsi untuk menghapus penduduk berdasarkan ID
    fun deletePendudukById(id: Int) {
        viewModelScope.launch {
            repository.deletePendudukById(id)
        }
    }

    fun searchPendudukByNIKAndRT(nik: String, rt: String): LiveData<List<Penduduk>> {
        return repository.searchPendudukByNIKAndRT(nik, rt)
    }

    fun getCountByRT(rt: String): LiveData<Int> {
        return repository.getCountByRT(rt)
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
