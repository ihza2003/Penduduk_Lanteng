package com.example.penduduk_lanteng.Analisis

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.penduduk_lanteng.DB.AppDatabase
import com.example.penduduk_lanteng.DB.repository.PendudukRepository
import com.example.penduduk_lanteng.data.data1.PendudukViewModel
import com.example.penduduk_lanteng.data.data1.PendudukViewModelFactory
import com.example.penduduk_lanteng.databinding.FragmentAnalisBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class AnalisFragment : Fragment() {

    private var _binding: FragmentAnalisBinding? = null
    private val binding get() = _binding!!
    private lateinit var pendudukViewModel: PendudukViewModel
    private lateinit var pieChart: PieChart

    private val entries = mutableListOf<PieEntry>() // List untuk menyimpan semua data RT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnalisBinding.inflate(inflater, container, false)

        // Inisialisasi ViewModel
        val appDatabase = AppDatabase.getInstance(requireContext())
        val pendudukDao = appDatabase?.pendudukDao()
        val repository = PendudukRepository(pendudukDao!!)
        pendudukViewModel = ViewModelProvider(this, PendudukViewModelFactory(repository)).get(PendudukViewModel::class.java)

        // Inisialisasi PieChart
        pieChart = binding.pieChart
        setupPieChart()

        // Observasi data untuk RT01
        pendudukViewModel.getCountByRT("1").observe(viewLifecycleOwner) { countRT1 ->
            binding.textViewRT01Count.text = "Total RT 01: $countRT1 Jiwa"
            entries.add(PieEntry(countRT1.toFloat(), "RT 01"))
            updatePieChart()
        }

        // Observasi data untuk RT02
        pendudukViewModel.getCountByRT("2").observe(viewLifecycleOwner) { countRT2 ->
            binding.textViewRT02Count.text = "Total RT 02: $countRT2 Jiwa"
            entries.add(PieEntry(countRT2.toFloat(), "RT 02"))
            updatePieChart()
        }

        // Observasi data untuk RT03
        pendudukViewModel.getCountByRT("3").observe(viewLifecycleOwner) { countRT3 ->
            binding.textViewRT03Count.text = "Total RT 03: $countRT3 Jiwa"
            entries.add(PieEntry(countRT3.toFloat(), "RT 03"))
            updatePieChart()
        }

        // Observasi data untuk RT04
        pendudukViewModel.getCountByRT("4").observe(viewLifecycleOwner) { countRT4 ->
            binding.textViewRT04Count.text = "Total RT 04: $countRT4 Jiwa"
            entries.add(PieEntry(countRT4.toFloat(), "RT 04"))
            updatePieChart()
        }

        return binding.root
    }

    private fun setupPieChart() {
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(15f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "Kependudukan"
        pieChart.setCenterTextSize(18f)
        pieChart.description.isEnabled = false

        // Atur properti legenda
        val legend = pieChart.legend
        legend.isEnabled = true
        legend.textSize = 14f // Ukuran teks legenda
        legend.formSize = 14f // Ukuran kotak warna di legenda
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL

        // Menambahkan margin antara legend items
        legend.xEntrySpace = 10f  // Mengatur jarak horizontal
        legend.yEntrySpace = 5f   // Mengatur jarak vertikal

        legend.setDrawInside(false)
    }

    private fun updatePieChart() {
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(Color.GREEN, Color.rgb(130,128,228), Color.RED, Color.YELLOW)
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueTextSize(17f)
        data.setValueTextColor(Color.BLACK)


        pieChart.data = data
        pieChart.invalidate() // refresh
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


