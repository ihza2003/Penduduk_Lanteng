package com.example.penduduk_lanteng.layanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.penduduk_lanteng.Analisis.AnalisFragment
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentNavigationParentBinding

class NavigationParentFragment : Fragment() {

    private var _binding: FragmentNavigationParentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNavigationParentBinding.inflate(inflater, container, false)

        setCurrentFragment(LayananFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> setCurrentFragment(LayananFragment())
                R.id.analis -> setCurrentFragment(AnalisFragment())
            }
            true
        }
        return binding.root
    }
    private fun setCurrentFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.parent_fragment, fragment)
            commit()
        }
    }

}