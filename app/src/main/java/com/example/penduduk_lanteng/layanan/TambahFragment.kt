package com.example.penduduk_lanteng.layanan

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.penduduk_lanteng.R
import com.example.penduduk_lanteng.databinding.FragmentTambahBinding
import java.util.Calendar


class TambahFragment : Fragment() {

    private var _binding : FragmentTambahBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTambahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Calendar dengan tanggal sekarang
        val calendar = Calendar.getInstance()

        // Set onClickListener pada EditText
        binding.imageButton.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Buat DatePickerDialog
            val datePickerDialog = DatePickerDialog(requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Set tanggal yang dipilih ke EditText
                    val date = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                    binding.InputTgl.setText(date)
                },
                year, month, day
            )

            // Tampilkan DatePickerDialog
            datePickerDialog.show()
        }
        // Inisialisasi Spinner
        val statusAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.status_options,
            android.R.layout.simple_spinner_item
        )
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.InputStatus.adapter = statusAdapter

        val rtAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.rt_options,
            android.R.layout.simple_spinner_item
        )
        rtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.InputRT.adapter = rtAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Hapus referensi binding untuk menghindari kebocoran memori
    }
}