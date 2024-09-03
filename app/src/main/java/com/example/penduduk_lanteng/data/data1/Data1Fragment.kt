package com.example.penduduk_lanteng.data.data1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penduduk_lanteng.DB.AppDatabase
import com.example.penduduk_lanteng.DB.entity.Penduduk
import com.example.penduduk_lanteng.DB.repository.PendudukRepository
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentData1Binding

class Data1Fragment : Fragment(), PendudukAdapter.OnItemClickListener {

    private var _binding: FragmentData1Binding? = null
    private val binding get() = _binding!!

    private lateinit var pendudukViewModel: PendudukViewModel
    private lateinit var adapter: PendudukAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentData1Binding.inflate(inflater, container, false)

        // Inisialisasi RecyclerView dan Adapter
        adapter = PendudukAdapter(emptyList(), this)
        binding.listRt1.layoutManager = LinearLayoutManager(context)
        binding.listRt1.adapter = adapter

        // Inisialisasi ViewModel
        val appDatabase = AppDatabase.getInstance(requireContext())
        val pendudukDao = appDatabase?.pendudukDao()
        val repository = PendudukRepository(pendudukDao!!)

        pendudukViewModel = ViewModelProvider(this, PendudukViewModelFactory(repository)).get(PendudukViewModel::class.java)

        // Observasi data dari ViewModel
        pendudukViewModel.getPendudukByRT("1").observe(viewLifecycleOwner, { pendudukList ->
            adapter.setPendudukData(pendudukList)
        })

        return binding.root
    }

    override fun onItemClick(penduduk: Penduduk) {
        val bundle = Bundle().apply {
            putParcelable("penduduk_data", penduduk)
        }
        findNavController().navigate(R.id.action_data1Fragment_to_detail1Fragment, bundle)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

