package com.example.penduduk_lanteng.DB.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    // Update data penduduk yang sudah ada
    @Update
    suspend fun updatePenduduk(penduduk: Penduduk)

    // Query untuk mendapatkan penduduk berdasarkan ID
    @Query("SELECT * FROM penduduk WHERE id = :id LIMIT 1")
    fun getPendudukById(id: Int): LiveData<Penduduk>

    // Fungsi untuk menghapus penduduk berdasarkan ID
    @Query("DELETE FROM penduduk WHERE id = :id")
    suspend fun deletePendudukById(id: Int)

//    buat search
    @Query("SELECT * FROM penduduk WHERE nik = :nik AND rt = :rt")
    fun searchPendudukByNIKAndRT(nik: String, rt: String): LiveData<List<Penduduk>>

    @Query("SELECT * FROM penduduk")
    suspend fun getAllPenduduk(): List<Penduduk>

    @Query("SELECT COUNT(*) FROM penduduk WHERE rt = :rt")
    fun getCountByRT(rt: String): LiveData<Int>

}