package com.example.penduduk_lanteng.Splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _binding : FragmentSplashBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(
                R.id.action_splashFragment_to_navigationParentFragment2,
                null,
                androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(R.id.splashFragment, true) // Menghapus SplashFragment dari back stack
                    .build()
            )

        }, 4000)
        return binding.root
    }

}