package com.example.penduduk_lanteng.DB.repository

import androidx.lifecycle.LiveData
import com.example.penduduk_lanteng.DB.dao.PendudukDao
import com.example.penduduk_lanteng.DB.entity.Penduduk

class PendudukRepository(private val pendudukDao: PendudukDao) {

    fun getPendudukByRT(rt: String): LiveData<List<Penduduk>> {
        return pendudukDao.getPendudukByRT(rt)
    }
}