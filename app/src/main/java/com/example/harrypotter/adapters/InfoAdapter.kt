package com.example.harrypotter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotter.databinding.InfoListBinding
import com.example.harrypotter.models.InfoModel

class InfoAdapter(private val context: Context, private val infoModel: List<InfoModel>) :
    RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    class ViewHolder(private val binding: InfoListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: InfoModel) {
            binding.apply {
                typeTv.text = model.type
                contentTv.text = model.content
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            InfoListBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = infoModel[position])
    }

    override fun getItemCount(): Int = infoModel.size
}