package com.example.penduduk_lanteng.layanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentLihatBinding

class LihatFragment : Fragment() {

    private var _binding : FragmentLihatBinding? = null
    private val binding get() = _binding!!

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLihatBinding.inflate(inflater, container, false)
        return binding.root
    }

}