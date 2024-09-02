package com.example.penduduk_lanteng.DB.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Penduduk")
data class Penduduk(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nama : String,
    val alias : String,
    val nik : String,
    val tempat_lahir : String,
    val tanggal_lahir : String,
    val agama : String,
    val pekerjaan : String,
    val kelamin : String,
    val rt : String,
    val status: String,
    val hidup : String

)
