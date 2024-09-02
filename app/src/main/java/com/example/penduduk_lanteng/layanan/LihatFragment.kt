package com.example.penduduk_lanteng.layanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentLihatBinding

class LihatFragment : Fragment() {

    private var _binding : FragmentLihatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLihatBinding.inflate(inflater, container, false)

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

        return binding.root
    }

}