package com.example.penduduk_lanteng.data.data1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.penduduk_lanteng.DB.entity.Penduduk
import com.example.penduduk_lanteng.databinding.ItemHomeBinding

class PendudukAdapter(
    private var pendudukList: List<Penduduk>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PendudukAdapter.PendudukViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(penduduk: Penduduk)
    }

    inner class PendudukViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(penduduk: Penduduk) {
            binding.nama.text = penduduk.nama
            binding.dRT1.text = penduduk.rt
            binding.kelamin.text = penduduk.kelamin
            binding.nik.text = penduduk.nik
            binding.hidup.text = penduduk.hidup

            // Set onClickListener pada itemView
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(penduduk)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendudukViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendudukViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendudukViewHolder, position: Int) {
        holder.bind(pendudukList[position])
    }

    override fun getItemCount(): Int {
        return pendudukList.size
    }

    fun setPendudukData(newPendudukList: List<Penduduk>) {
        pendudukList = newPendudukList
        notifyDataSetChanged()
    }
}

