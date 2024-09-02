package com.example.penduduk_lanteng.data.data3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penduduk_lanteng.DB.AppDatabase
import com.example.penduduk_lanteng.DB.repository.PendudukRepository
import com.example.penduduk_lanteng.data.data1.PendudukAdapter
import com.example.penduduk_lanteng.data.data1.PendudukViewModel
import com.example.penduduk_lanteng.data.data1.PendudukViewModelFactory
import com.example.penduduk_lanteng.databinding.FragmentData3Binding

class Data3Fragment : Fragment() {

    private var _binding: FragmentData3Binding? = null
    private val binding get() = _binding!!

    private lateinit var pendudukViewModel: PendudukViewModel
    private lateinit var adapter: PendudukAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentData3Binding.inflate(inflater, container, false)

        // Inisialisasi RecyclerView dan Adapter
        adapter = PendudukAdapter(emptyList())
        binding.listRt1.layoutManager = LinearLayoutManager(context)
        binding.listRt1.adapter = adapter

        // Inisialisasi ViewModel
        val appDatabase = AppDatabase.getInstance(requireContext())
        val pendudukDao = appDatabase?.pendudukDao()
        val repository = PendudukRepository(pendudukDao!!)

        pendudukViewModel = ViewModelProvider(this, PendudukViewModelFactory(repository)).get(
            PendudukViewModel::class.java)

        // Observasi data dari ViewModel
        pendudukViewModel.getPendudukByRT("3").observe(viewLifecycleOwner, { pendudukList ->
            adapter.setPendudukData(pendudukList)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}