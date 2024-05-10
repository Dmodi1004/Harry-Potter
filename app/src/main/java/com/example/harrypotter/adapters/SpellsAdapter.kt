package com.example.harrypotter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotter.SpellDetailsDialog
import com.example.harrypotter.databinding.SpellListBinding
import com.example.harrypotter.models.SpellsModel

class SpellsAdapter(private val context: Context, private val spellsModel: List<SpellsModel>) :
    RecyclerView.Adapter<SpellsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: SpellListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, model: SpellsModel) {
            binding.apply {
                spellNameTv.text = model.use

                mainLayout.setOnClickListener {
                    val spellDetailsDialog = SpellDetailsDialog(context, model)
                    spellDetailsDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    spellDetailsDialog.show()
                }


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SpellListBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context = context, model = spellsModel[position])
    }

    override fun getItemCount(): Int = spellsModel.size

}