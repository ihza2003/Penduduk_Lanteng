package com.example.penduduk_lanteng.layanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentLayananBinding
import com.example.penduduk_lanteng.databinding.FragmentSplashBinding


class LayananFragment : Fragment() {
    private var _binding : FragmentLayananBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLayananBinding.inflate(inflater, container, false)

        binding.people.setOnClickListener {
            findNavController().navigate(R.id.action_navigationParentFragment_to_tambahFragment)
        }

        binding.people1.setOnClickListener {
            findNavController().navigate(R.id.action_navigationParentFragment_to_lihatFragment)
        }
        return binding.root
    }


}