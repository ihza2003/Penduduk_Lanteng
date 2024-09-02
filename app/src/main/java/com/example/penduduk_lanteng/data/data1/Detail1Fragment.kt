package com.example.penduduk_lanteng.data.data1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.penduduk_lanteng.DB.entity.Penduduk
import com.example.penduduk_lanteng.databinding.FragmentDetail1Binding

class Detail1Fragment : Fragment() {

    private var _binding: FragmentDetail1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetail1Binding.inflate(inflater, container, false)

        // Ambil data penduduk dari arguments
        val penduduk = arguments?.getParcelable<Penduduk>("penduduk_data")

        penduduk?.let {
            binding.dNama.text = it.nama
            binding.dAlias.text = it.alias
            binding.dNik.text = it.nik
            binding.dTempat.text = it.tempat_lahir
            binding.dTgl.text = it.tanggal_lahir
            binding.dAgm.text = it.agama
            binding.dPekerjaan.text = it.pekerjaan
            binding.dkelamin.text = it.kelamin
            binding.dRT.text = it.rt
            binding.dStatus.text = it.status
            binding.dHidup.text = it.hidup
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
