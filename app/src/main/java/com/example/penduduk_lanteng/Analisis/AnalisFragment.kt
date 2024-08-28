package com.example.penduduk_lanteng.Analisis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentAnalisBinding
import com.example.penduduk_lanteng.databinding.FragmentLayananBinding


class AnalisFragment : Fragment() {

    private var _binding : FragmentAnalisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnalisBinding.inflate(inflater, container, false)
        return binding.root
    }


}