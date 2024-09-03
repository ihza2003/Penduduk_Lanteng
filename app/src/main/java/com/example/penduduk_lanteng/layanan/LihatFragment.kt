package com.example.penduduk_lanteng.layanan

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentLihatBinding
import jxl.Workbook
import jxl.write.Label
import jxl.write.WritableWorkbook
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class LihatFragment : Fragment() {

    private var _binding : FragmentLihatBinding? = null
    private val binding get() = _binding!!
    private val CREATE_FILE_REQUEST_CODE = 1
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


        return binding.root
    }

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }

    private fun exportToDownloads() {
        lifecycleScope.launch {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, "DataPenduduk.xls")
            val uri = Uri.fromFile(file)

            writeDataToExcel(uri)
        }
    }

    private suspend fun writeDataToExcel(uri: Uri) {
        try {
            val outputStream: OutputStream = FileOutputStream(File(uri.path!!))
            val workbook: WritableWorkbook = Workbook.createWorkbook(outputStream)
            val sheet = workbook.createSheet("Data Penduduk", 0)

            // Membuat Header
            val headers = listOf("NIK", "Nama", "Alias", "Tempat Lahir", "Tanggal Lahir", "Agama", "Pekerjaan", "Kelamin", "RT", "Status", "Hidup")
            headers.forEachIndexed { index, header ->
                sheet.addCell(Label(index, 0, header))
            }

            // Ambil data penduduk dari database
            val pendudukList = pendudukDao.getAllPenduduk()
            pendudukList.forEachIndexed { rowIndex, penduduk ->
                sheet.addCell(Label(0, rowIndex + 1, penduduk.nik))
                sheet.addCell(Label(1, rowIndex + 1, penduduk.nama))
                sheet.addCell(Label(2, rowIndex + 1, penduduk.alias))
                sheet.addCell(Label(3, rowIndex + 1, penduduk.tempat_lahir))
                sheet.addCell(Label(4, rowIndex + 1, penduduk.tanggal_lahir))
                sheet.addCell(Label(5, rowIndex + 1, penduduk.agama))
                sheet.addCell(Label(6, rowIndex + 1, penduduk.pekerjaan))
                sheet.addCell(Label(7, rowIndex + 1, penduduk.kelamin))
                sheet.addCell(Label(8, rowIndex + 1, penduduk.rt))
                sheet.addCell(Label(9, rowIndex + 1, penduduk.status))
                sheet.addCell(Label(10, rowIndex + 1, penduduk.hidup))
            }

            // Menyimpan Workbook
            workbook.write()
            workbook.close()
            outputStream.close()

            Toast.makeText(requireContext(), "Data berhasil diekspor", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Gagal mengekspor data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}