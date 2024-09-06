package com.example.penduduk_lanteng.layanan

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.penduduk_lanteng.DB.AppDatabase
import com.example.penduduk_lanteng.DB.entity.Penduduk
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentTambahBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar


class TambahFragment : Fragment() {

    private var _binding : FragmentTambahBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTambahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Calendar dengan tanggal sekarang
        val calendar = Calendar.getInstance()

        // Set onClickListener pada EditText
        binding.imageButton.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Buat DatePickerDialog
            val datePickerDialog = DatePickerDialog(requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Set tanggal yang dipilih ke EditText
                    val date = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                    binding.InputTgl.setText(date)
                },
                year, month, day
            )

            // Tampilkan DatePickerDialog
            datePickerDialog.show()
        }
        // Inisialisasi Spinner

        val kelaminAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.kelamin_options,
            android.R.layout.simple_spinner_item
        )
        kelaminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.InputKelamin.adapter = kelaminAdapter

        val statusAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.status_options,
            android.R.layout.simple_spinner_item
        )
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.InputStatus.adapter = statusAdapter

        val rtAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.rt_options,
            android.R.layout.simple_spinner_item
        )
        rtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.InputRT.adapter = rtAdapter

        val hidupAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.hidup_options,
            android.R.layout.simple_spinner_item
        )
        hidupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.InputHidup.adapter = hidupAdapter

        binding.tambah.setOnClickListener {
            tambahPenduduk()

        }


    }
    private fun tambahPenduduk() {
        val nama = binding.InputNama.text.toString().trim()
        val alias = binding.InputAlias.text.toString().trim()
        val nik = binding.InputNIK.text.toString().trim()
        val tempatLahir = binding.InputTmpt.text.toString().trim()
        val tanggalLahir = binding.InputTgl.text.toString().trim()
        val agama = binding.InputAgm.text.toString().trim()
        val pekerjaan = binding.InputPekerjaan.text.toString().trim()
        val kelamin = binding.InputKelamin.selectedItem.toString().trim()
        val rt = binding.InputRT.selectedItem.toString().trim()
        val status = binding.InputStatus.selectedItem.toString().trim()
        val hidup = binding.InputHidup.selectedItem.toString().trim()

        // Validasi input termasuk validasi RT dan Status
        if (nama.isEmpty() || alias.isEmpty() || nik.isEmpty() || tempatLahir.isEmpty() ||
            tanggalLahir.isEmpty() || agama.isEmpty() || pekerjaan.isEmpty() ||
            kelamin == "Pilih Kelamin"|| rt == "Pilih RT" || status == "Pilih Status" || hidup == "Status Hidup") {
            Toast.makeText(requireContext(), "Semua inputan harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        // Validasi panjang NIK harus 16 digit
        if (nik.length != 16) {
            Toast.makeText(requireContext(), "NIK harus terdiri dari 16 digit!", Toast.LENGTH_SHORT).show()
            return
        }

        val penduduk = Penduduk(
            id = 0, // ID auto-generate
            nama = nama,
            alias = alias,
            nik = nik,
            tempat_lahir = tempatLahir,
            tanggal_lahir = tanggalLahir,
            agama = agama,
            pekerjaan = pekerjaan,
            kelamin = kelamin,
            rt = rt,
            status = status,
            hidup = hidup
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val db = AppDatabase.getInstance(requireContext())
                val existingPenduduk = db?.pendudukDao()?.getPendudukByNik(nik)

                if (existingPenduduk != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Data Penduduk dengan NIK ini sudah ada!", Toast.LENGTH_SHORT).show()
                    }
                    return@withContext
                }
                db?.pendudukDao()?.insertPenduduk(penduduk)
            }

            findNavController().navigate(R.id.action_tambahFragment_to_navigationParentFragment)
            Toast.makeText(requireContext(), "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Hapus referensi binding untuk menghindari kebocoran memori
    }
}