package com.example.penduduk_lanteng.DB.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.penduduk_lanteng.DB.entity.Penduduk

@Dao
interface PendudukDao {

    // Query untuk memasukan data ke database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPenduduk(penduduk: Penduduk)

    // Query untuk menvalidasi digit nik
    @Query("SELECT * FROM penduduk WHERE nik = :nik LIMIT 1")
    fun getPendudukByNik(nik: String): Penduduk?

    // Query untuk mendapatkan penduduk berdasarkan RT
    @Query("SELECT * FROM penduduk WHERE rt = :rt")
    fun getPendudukByRT(rt: String): LiveData<List<Penduduk>>


}