package com.example.penduduk_lanteng.layanan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.penduduk_lanteng.DB.AppDatabase
import com.example.penduduk_lanteng.DB.dao.PendudukDao
import com.example.penduduk_lanteng.DB.entity.Penduduk
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentLihatBinding
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class LihatFragment : Fragment() {

    private var _binding: FragmentLihatBinding? = null
    private val binding get() = _binding!!
    private val CREATE_FILE_REQUEST_CODE = 1
    private val IMPORT_FILE_REQUEST_CODE = 2
    private var isHeaderPresent: Boolean = false
    private lateinit var pendudukDao: PendudukDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLihatBinding.inflate(inflater, container, false)

        // Inisialisasi PendudukDao menggunakan AppDatabase
        val db = AppDatabase.getInstance(requireContext())
        pendudukDao = db?.pendudukDao() ?: throw IllegalStateException("Database not initialized")

        binding.rt1.setOnClickListener {
            findNavController().navigate(R.id.action_lihatFragment_to_data1Fragment)
        }

        binding.rt2.setOnClickListener {
            findNavController().navigate(R.id.action_lihatFragment_to_data2Fragment)
        }

        binding.rt3.setOnClickListener {
            findNavController().navigate(R.id.action_lihatFragment_to_data3Fragment)
        }

        binding.rt4.setOnClickListener {
            findNavController().navigate(R.id.action_lihatFragment_to_data4Fragment)
        }

        binding.buttonExportToExcel.setOnClickListener {
            exportToDownloads()
        }

        binding.buttonImportFromExcel.setOnClickListener {
            openFilePicker()
        }

        return binding.root
    }

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

    private fun exportToDownloads() {
        lifecycleScope.launch {
            // Mendapatkan direktori Downloads
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            // Membuat nama file unik dengan menggunakan timestamp
            val timestamp = System.currentTimeMillis() // Anda juga bisa menggunakan UUID.randomUUID().toString() untuk nama file yang lebih unik
            val fileName = "DataPenduduk_$timestamp.xlsx"
            val file = File(downloadsDir, fileName)
            val uri = Uri.fromFile(file)

            writeDataToExcel(uri)
        }
    }


    private suspend fun writeDataToExcel(uri: Uri) {
        try {
            val outputStream: OutputStream = FileOutputStream(File(uri.path!!))

            // Buat Workbook menggunakan Apache POI
            val workbook = org.apache.poi.xssf.usermodel.XSSFWorkbook()
            val sheet = workbook.createSheet("Data Penduduk")

            // Membuat Cell Style untuk header dengan border
            val headerCellStyle = workbook.createCellStyle().apply {
                val font = workbook.createFont().apply {
                    bold = true
                }
                setFont(font)
                borderTop = org.apache.poi.ss.usermodel.BorderStyle.THIN
                borderBottom = org.apache.poi.ss.usermodel.BorderStyle.THIN
                borderLeft = org.apache.poi.ss.usermodel.BorderStyle.THIN
                borderRight = org.apache.poi.ss.usermodel.BorderStyle.THIN
            }

            // Membuat Cell Style untuk data dengan border
            val dataCellStyle = workbook.createCellStyle().apply {
                borderTop = org.apache.poi.ss.usermodel.BorderStyle.THIN
                borderBottom = org.apache.poi.ss.usermodel.BorderStyle.THIN
                borderLeft = org.apache.poi.ss.usermodel.BorderStyle.THIN
                borderRight = org.apache.poi.ss.usermodel.BorderStyle.THIN
            }

            // Membuat Header jika belum ada
            if (!isHeaderPresent) {
                val headers = listOf(
                    "NIK", "Nama", "Alias", "Tempat Lahir",
                    "Tanggal Lahir", "Agama", "Pekerjaan",
                    "Kelamin", "RT", "Status", "Status Hidup"
                )

                val headerRow = sheet.createRow(0)

                headers.forEachIndexed { index, header ->
                    val cell = headerRow.createCell(index)
                    cell.setCellValue(header)
                    cell.cellStyle = headerCellStyle // Terapkan style bold dan border ke header
                }

                // Freeze header row
                sheet.createFreezePane(0, 1)
            }

            // Ambil data penduduk dari database
            val pendudukList = pendudukDao.getAllPenduduk()
            pendudukList.forEachIndexed { rowIndex, penduduk ->
                val row = sheet.createRow(rowIndex + 1)
                row.createCell(0).apply {
                    setCellValue(penduduk.nik)
                    cellStyle = dataCellStyle // Terapkan border ke cell
                }
                row.createCell(1).apply {
                    setCellValue(penduduk.nama)
                    cellStyle = dataCellStyle
                }
                row.createCell(2).apply {
                    setCellValue(penduduk.alias)
                    cellStyle = dataCellStyle
                }
                row.createCell(3).apply {
                    setCellValue(penduduk.tempat_lahir)
                    cellStyle = dataCellStyle
                }
                row.createCell(4).apply {
                    setCellValue(penduduk.tanggal_lahir)
                    cellStyle = dataCellStyle
                }
                row.createCell(5).apply {
                    setCellValue(penduduk.agama)
                    cellStyle = dataCellStyle
                }
                row.createCell(6).apply {
                    setCellValue(penduduk.pekerjaan)
                    cellStyle = dataCellStyle
                }
                row.createCell(7).apply {
                    setCellValue(penduduk.kelamin)
                    cellStyle = dataCellStyle
                }
                row.createCell(8).apply {
                    setCellValue(penduduk.rt)
                    cellStyle = dataCellStyle
                }
                row.createCell(9).apply {
                    setCellValue(penduduk.status)
                    cellStyle = dataCellStyle
                }
                row.createCell(10).apply {
                    setCellValue(penduduk.hidup)
                    cellStyle = dataCellStyle
                }
            }

            // Atur lebar kolom secara otomatis
            sheet.setColumnWidth(0, 5000) // Menetapkan lebar kolom pertama
            sheet.setColumnWidth(1, 5000) // Menetapkan lebar kolom kedua, dan seterusnya
            sheet.setColumnWidth(2, 5000)
            sheet.setColumnWidth(3, 5000)
            sheet.setColumnWidth(4, 5000)
            sheet.setColumnWidth(5, 5000)
            sheet.setColumnWidth(6, 5000)
            sheet.setColumnWidth(7, 5000)
            sheet.setColumnWidth(8, 2000)
            sheet.setColumnWidth(9, 5000)
            sheet.setColumnWidth(10, 5000)



            // Simpan Workbook ke file
            workbook.write(outputStream)
            workbook.close()
            outputStream.close()

            Toast.makeText(requireContext(), "Data berhasil diekspor", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Gagal mengekspor data", Toast.LENGTH_SHORT).show()
        }
    }


    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // .xlsx

            putExtra(
                Intent.EXTRA_MIME_TYPES, arrayOf(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xlsx
                    "application/vnd.ms-excel" // .xls
                )
            )
        }
        startActivityForResult(intent, IMPORT_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMPORT_FILE_REQUEST_CODE && resultCode == android.app.Activity.RESULT_OK && data != null) {
            val uri = data.data
            uri?.let { readExcelFile(it) }
        }
    }

    private fun readExcelFile(uri: Uri) {
        try {
            val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
            val workbook = WorkbookFactory.create(inputStream)
            val sheet = workbook.getSheetAt(0)

            // Cek apakah baris pertama adalah header
            val firstRow = sheet.getRow(0)
            val isFirstRowHeader = firstRow?.getCell(0)?.stringCellValue.equals("NIK", ignoreCase = true)

            if (isFirstRowHeader) {
                isHeaderPresent = true
            }

            // Iterasi melalui setiap baris di sheet (mulai dari baris ke-2 jika ada header)
            for (i in 1 until sheet.physicalNumberOfRows) {
                val row = sheet.getRow(i)
                val nik = row.getCell(0).stringCellValue
                val nama = row.getCell(1).stringCellValue
                val alias = row.getCell(2).stringCellValue
                val tempatLahir = row.getCell(3).stringCellValue
                val tanggalLahir = row.getCell(4).stringCellValue
                val agama = row.getCell(5).stringCellValue
                val pekerjaan = row.getCell(6).stringCellValue
                val kelamin = row.getCell(7).stringCellValue
                val rt = row.getCell(8).stringCellValue
                val status = row.getCell(9).stringCellValue
                val hidup = row.getCell(10).stringCellValue

                // Buat objek Penduduk dari data yang dibaca
                val penduduk = Penduduk(
                    id = 0,
                    nik = nik,
                    nama = nama,
                    alias = alias,
                    tempat_lahir = tempatLahir,
                    tanggal_lahir = tanggalLahir,
                    agama = agama,
                    pekerjaan = pekerjaan,
                    kelamin = kelamin,
                    rt = rt,
                    status = status,
                    hidup = hidup
                )

                // Simpan objek penduduk ke database
                lifecycleScope.launch {
                    pendudukDao.insertPenduduk(penduduk)
                }
            }

            Toast.makeText(requireContext(), "Import berhasil", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("Excel Error", "Error reading Excel file: ", e)
            Toast.makeText(requireContext(), "Gagal membaca file Excel", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}