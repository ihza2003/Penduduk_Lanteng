package com.example.penduduk_lanteng.DB.entity
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "Penduduk")
@Parcelize
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

) : Parcelable
