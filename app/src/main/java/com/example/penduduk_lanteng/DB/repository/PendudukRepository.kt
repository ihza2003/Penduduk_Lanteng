package com.example.penduduk_lanteng.DB.repository

import androidx.lifecycle.LiveData
import com.example.penduduk_lanteng.DB.dao.PendudukDao
import com.example.penduduk_lanteng.DB.entity.Penduduk

class PendudukRepository(private val pendudukDao: PendudukDao) {

    fun getPendudukByRT(rt: String): LiveData<List<Penduduk>> {
        return pendudukDao.getPendudukByRT(rt)
    }

    suspend fun updatePenduduk(penduduk: Penduduk) {
        return pendudukDao.updatePenduduk(penduduk)
    }

    // Fungsi untuk mendapatkan penduduk berdasarkan ID
    fun getPendudukById(id: Int): LiveData<Penduduk> {
        return pendudukDao.getPendudukById(id)
    }

    // Fungsi untuk menghapus penduduk berdasarkan ID
    suspend fun deletePendudukById(id: Int) {
        pendudukDao.deletePendudukById(id)
    }

    //untuk search nik berdasarkan rt
    fun searchPendudukByNIKAndRT(nik: String, rt: String): LiveData<List<Penduduk>> {
        return pendudukDao.searchPendudukByNIKAndRT(nik, rt)
    }

    fun getCountByRT(rt: String): LiveData<Int> {
        return pendudukDao.getCountByRT(rt)
    }



}