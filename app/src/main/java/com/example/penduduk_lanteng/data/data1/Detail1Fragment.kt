package com.example.penduduk_lanteng.data.data1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.penduduk_lanteng.databinding.FragmentDetail1Binding

class Detail1Fragment : Fragment() {

    private var _binding: FragmentDetail1Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetail1Binding.inflate(inflater, container, false)
        return binding.root
    }

}