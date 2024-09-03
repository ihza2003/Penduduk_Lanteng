package com.example.penduduk_lanteng.data.data1

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.penduduk_lanteng.DB.AppDatabase
import com.example.penduduk_lanteng.DB.entity.Penduduk
import com.example.penduduk_lanteng.DB.repository.PendudukRepository
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentDetail1Binding

class Detail1Fragment : Fragment() {

    private var _binding: FragmentDetail1Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PendudukViewModel
    private var pendudukId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetail1Binding.inflate(inflater, container, false)

        // Ambil data penduduk dari arguments
        val penduduk = arguments?.getParcelable<Penduduk>("penduduk_data")

        pendudukId = penduduk?.id ?: 0

        // Inisialisasi ViewModel
        val appDatabase = AppDatabase.getInstance(requireContext())
        val pendudukDao = appDatabase?.pendudukDao()
        val repository = PendudukRepository(pendudukDao!!)
        viewModel = ViewModelProvider(this, PendudukViewModelFactory(repository)).get(PendudukViewModel::class.java)


        // Mengamati data penduduk berdasarkan ID
        viewModel.getPendudukById(pendudukId).observe(viewLifecycleOwner) { updatedPenduduk ->
            updatedPenduduk?.let {
                updateUI(it)
            }
        }

        binding.edit.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("penduduk_data", penduduk)
            }
            findNavController().navigate(R.id.action_detail1Fragment_to_editFragment, bundle)
        }

        // Tambahkan Listener untuk tombol hapus
        binding.Hapus.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        return binding.root
    }

    // Fungsi untuk memperbarui UI dengan data penduduk terbaru
    private fun updateUI(penduduk: Penduduk) {
        binding.dNama.text = penduduk.nama
        binding.dAlias.text = penduduk.alias
        binding.dNik.text = penduduk.nik
        binding.dTempat.text = penduduk.tempat_lahir
        binding.dTgl.text = penduduk.tanggal_lahir
        binding.dAgm.text = penduduk.agama
        binding.dPekerjaan.text = penduduk.pekerjaan
        binding.dkelamin.text = penduduk.kelamin
        binding.dRT.text = penduduk.rt
        binding.dStatus.text = penduduk.status
        binding.dHidup.text = penduduk.hidup


    }

    private fun showDeleteConfirmationDialog() {
        // Membuat dialog konfirmasi penghapusan
        AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_warning_red)
            .setTitle("Hapus Data")
            .setMessage("Apakah yakin ingin menghapus data ini?")
            .setPositiveButton("Ya") { _, _ ->
                deletePenduduk()
            }
            .setNegativeButton("Tidak", null)
            .show()
            .also { dialog ->
                // Mengubah warna tombol "Ya" menjadi merah
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.red))
            }
    }

    private fun deletePenduduk() {
        viewModel.deletePendudukById(pendudukId)
        findNavController().popBackStack() // Kembali ke fragment sebelumnya setelah penghapusan
        Toast.makeText(requireContext(), "Data berhasil dihapus!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
