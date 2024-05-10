/*
package com.example.harrypotter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotter.R
import com.example.harrypotter.models.CharactersModel
import com.squareup.picasso.Picasso

class CharactersAdapter(
    private val context: Context,
    private val charactersModel: List<CharactersModel>
) : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {




    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterNameTv: TextView = itemView.findViewById(R.id.characterNameTv)
        val characterImage: ImageView = itemView.findViewById(R.id.characterImage)
        val nicknameTv: TextView = itemView.findViewById(R.id.nicknameTv)
        val houseNameTv: TextView = itemView.findViewById(R.id.houseNameTv)
        val childNameTv: TextView = itemView.findViewById(R.id.childNameTv)
        val interpretedByTv: TextView = itemView.findViewById(R.id.interpretedByTv)
        val expandableLayout: LinearLayout = itemView.findViewById(R.id.expandableLayout)
        val mainLayout: ConstraintLayout = itemView.findViewById(R.id.mainLayout)
        val childLayout: LinearLayout = itemView.findViewById(R.id.childLayout)

        fun collapseExpandedView() {
            expandableLayout.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(

            LayoutInflater.from(context).inflate(R.layout.character_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val characterData = charactersModel[position]
        holder.characterNameTv.text = characterData.character
        Picasso.get()
            .load(characterData.image)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.characterImage)
        holder.nicknameTv.text = characterData.nickname
        holder.houseNameTv.text = characterData.hogwartsHouse

        if (characterData.child.isEmpty()) {
            holder.childLayout.visibility = View.GONE
        } else {
            holder.childNameTv.text = characterData.child.joinToString()
        }
        holder.interpretedByTv.text = characterData.interpretedBy

        val isExpandable: Boolean = characterData.isExpandable
        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.mainLayout.setOnClickListener {
            isAnyItemExpanded(position)
            characterData.isExpandable = !characterData.isExpandable
            notifyItemChanged(position)
        }

    }

    private fun isAnyItemExpanded(position: Int) {
        val temp = charactersModel.indexOfFirst {
            it.isExpandable
        }

        if (temp >= 0 && temp != position) {
            charactersModel[temp].isExpandable = false
            notifyItemChanged(temp, 0)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {

        if (payloads.isNotEmpty() && payloads[0] == 0) {
            holder.collapseExpandedView()
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int = charactersModel.size

}*/


package com.example.harrypotter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotter.R
import com.example.harrypotter.databinding.CharacterListBinding
import com.example.harrypotter.models.CharactersModel
import com.squareup.picasso.Picasso

class CharactersAdapter(
    private val context: Context,
    private val charactersModel: List<CharactersModel>
) : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    private var isAnyItemExpanded = false

    class ViewHolder(val binding: CharacterListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharactersModel, isItemExpanded: Boolean) {
            binding.apply {
                characterNameTv.text = character.character
                Picasso.get()
                    .load(character.image)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(characterImage)
                nicknameTv.text = character.nickname
                houseNameTv.text = character.hogwartsHouse

                if (character.child!!.isEmpty()) {
                    childLayout.visibility = View.GONE
                } else {
                    childNameTv.text = character.child.joinToString(separator = ",\n")
                    childLayout.visibility = View.VISIBLE
                }
                interpretedByTv.text = character.interpretedBy

                expandableLayout.visibility = if (isItemExpanded) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CharacterListBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = charactersModel[position]
        holder.bind(character, isAnyItemExpanded && character.isExpandable)

        holder.binding.mainLayout.setOnClickListener {
            if (isAnyItemExpanded && isAnyItemExpanded != character.isExpandable) {
                collapseExpandedItem()
            }
            character.isExpandable = !character.isExpandable
            isAnyItemExpanded = character.isExpandable
            notifyItemChanged(position)
        }
    }

    private fun collapseExpandedItem() {
        val expandedIndex = charactersModel.indexOfFirst { it.isExpandable }
        if (expandedIndex >= 0) {
            charactersModel[expandedIndex].isExpandable = false
            notifyItemChanged(expandedIndex)
        }
        isAnyItemExpanded = false
    }

    override fun getItemCount(): Int = charactersModel.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty() && payloads[0] == 0) {
            holder.bind(charactersModel[position], false)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}
