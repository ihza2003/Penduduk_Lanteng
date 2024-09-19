package com.example.penduduk_lanteng.layanan

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.penduduk_lanteng.DB.AppDatabase
import com.example.penduduk_lanteng.DB.entity.Penduduk
import com.example.penduduk_lanteng.DB.repository.PendudukRepository
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.data.data1.PendudukViewModel
import com.example.penduduk_lanteng.data.data1.PendudukViewModelFactory
import com.example.penduduk_lanteng.databinding.FragmentEditBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var penduduk: Penduduk
    private lateinit var viewModel: PendudukViewModel
    private var pendudukId: Int = 0  // To store the ID of the selected Penduduk

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
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
                    binding.EditTgl.setText(date)
                },
                year, month, day
            )

            // Tampilkan DatePickerDialog
            datePickerDialog.show()
        }



        // Ambil data penduduk dari arguments
        penduduk = arguments?.getParcelable("penduduk_data")!!

        // Inisialisasi ViewModel
        val appDatabase = AppDatabase.getInstance(requireContext())
        val pendudukDao = appDatabase?.pendudukDao()
        val repository = PendudukRepository(pendudukDao!!)

        viewModel = ViewModelProvider(this, PendudukViewModelFactory(repository)).get(PendudukViewModel::class.java)

        // Ambil ID penduduk dari argument yang dikirim dari Detail1Fragment
        pendudukId = penduduk?.id ?: 0


        // Mengamati data penduduk berdasarkan ID
        viewModel.getPendudukById(pendudukId).observe(viewLifecycleOwner) { updatedPenduduk ->
            updatedPenduduk?.let {
                updateUI(it)
            }
        }

        // Simpan perubahan
        binding.Edit.setOnClickListener {
            saveChanges()
        }

        return binding.root
    }

    private fun updateUI (penduduk: Penduduk) {
        // Inisialisasi Spinner
        val agamaAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.agama_options,
            android.R.layout.simple_spinner_item
        )
        agamaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.EditAgm.adapter = agamaAdapter

        val kelaminAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.kelamin_options,
            android.R.layout.simple_spinner_item
        )
        kelaminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.EditKelamin.adapter = kelaminAdapter

        val statusAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.status_options,
            android.R.layout.simple_spinner_item
        )
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.EditStatus.adapter = statusAdapter

        val rtAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.rt_options,
            android.R.layout.simple_spinner_item
        )
        rtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.EditRT.adapter = rtAdapter

        val hidupAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.hidup_options,
            android.R.layout.simple_spinner_item
        )
        hidupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.EditHidup.adapter = hidupAdapter

        val golonganAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gol_options,
            android.R.layout.simple_spinner_item
        )
        golonganAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.EditGoldarah.adapter = golonganAdapter

        // Tampilkan data awal di form edit
        binding.EditNama.setText(penduduk.nama)
        binding.EditAlias.setText(penduduk.alias)
        binding.EditNIK.setText(penduduk.nik)
        binding.EditNoKK.setText(penduduk.kk)
        binding.EditTmpt.setText(penduduk.tempat_lahir)
        binding.EditTgl.setText(penduduk.tanggal_lahir)

        val agamaIndex = agamaAdapter.getPosition(penduduk.agama)
        binding.EditAgm.setSelection(agamaIndex)

        binding.EditPendidikan.setText(penduduk.pendidikan)
        binding.EditPekerjaan.setText(penduduk.pekerjaan)

        // Set Spinner values
        val kelaminIndex = kelaminAdapter.getPosition(penduduk.kelamin)
        binding.EditKelamin.setSelection(kelaminIndex)

        val golonganIndex = golonganAdapter.getPosition(penduduk.gol_darah)
        binding.EditGoldarah.setSelection(golonganIndex)

        binding.EditAyah.setText(penduduk.ayah)
        binding.EditIbu.setText(penduduk.ibu)

        val rtIndex = rtAdapter.getPosition(penduduk.rt)
        binding.EditRT.setSelection(rtIndex)

        val statusIndex = statusAdapter.getPosition(penduduk.status)
        binding.EditStatus.setSelection(statusIndex)

        binding.EditKeluarga.setText(penduduk.keluarga)

        val hidupIndex = hidupAdapter.getPosition(penduduk.hidup)
        binding.EditHidup.setSelection(hidupIndex)

    }


    private fun saveChanges() {
        val nama = binding.EditNama.text.toString().trim()
        val alias =binding.EditAlias.text.toString().trim()
        val nik = binding.EditNIK.text.toString().trim()
        val kk = binding.EditNoKK.text.toString().trim()
        val tempat = binding.EditTmpt.text.toString().trim()
        val tanggal = binding.EditTgl.text.toString().trim()
        val agama = binding.EditAgm.selectedItem.toString().trim()
        val pendidikan = binding.EditPendidikan.text.toString().trim()
        val pekerjaan = binding.EditPekerjaan.text.toString().trim()
        val kelamin = binding.EditKelamin.selectedItem.toString().trim()
        val gol_darah = binding.EditGoldarah.selectedItem.toString().trim()
        val ayah = binding.EditAyah.text.toString().trim()
        val ibu = binding.EditIbu.text.toString().trim()
        val rt = binding.EditRT.selectedItem.toString().trim()
        val status = binding.EditStatus.selectedItem.toString().trim()
        val keluarga = binding.EditKeluarga.text.toString().trim()
        val hidup = binding.EditHidup.selectedItem.toString().trim()

        if (nama.isEmpty() || nik.isEmpty() ||kk.isEmpty() || tempat.isEmpty() ||
            tanggal.isEmpty() || agama == "Pilih Agama" || pendidikan.isEmpty() || pekerjaan.isEmpty() ||
            kelamin == "Pilih Kelamin"|| gol_darah == "Pilih Golongan Darah" || ayah.isEmpty() || ibu.isEmpty() ||
            rt == "Pilih RT" || status == "Pilih Status" || keluarga.isEmpty() || hidup == "Status Hidup") {
            Toast.makeText(requireContext(), "Harap isi semua field!", Toast.LENGTH_SHORT).show()
            return
        }

        // Validasi panjang NIK harus 16 digit
        if (nik.length != 16) {
            Toast.makeText(requireContext(), "NIK harus terdiri dari 16 digit!", Toast.LENGTH_SHORT).show()
            return
        }

        // Lakukan pengecekan NIK di database
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val db = AppDatabase.getInstance(requireContext())
                val existingPenduduk = db?.pendudukDao()?.getPendudukByNik(nik)

                // Jika NIK sudah ada dan bukan milik penduduk yang sedang diedit
                if (existingPenduduk != null && existingPenduduk.id != pendudukId) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Data Penduduk dengan NIK ini sudah ada!", Toast.LENGTH_SHORT).show()
                    }
                    return@withContext
                }

                // Mengubah data penduduk
                val updatedPenduduk = Penduduk(
                    penduduk.id,
                    nama,
                    alias,
                    nik,
                    kk,
                    tempat,
                    tanggal,
                    agama,
                    pendidikan,
                    pekerjaan,
                    kelamin,
                    gol_darah,
                    ayah,
                    ibu,
                    rt,
                    status,
                    keluarga,
                    hidup
                )

                // Simpan perubahan ke database
                db?.pendudukDao()?.updatePenduduk(updatedPenduduk)

                withContext(Dispatchers.Main) {
                    // Navigasi kembali ke Detail1Fragment
                    findNavController().popBackStack()
                    Toast.makeText(requireContext(), "Data berhasil di Edit!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}