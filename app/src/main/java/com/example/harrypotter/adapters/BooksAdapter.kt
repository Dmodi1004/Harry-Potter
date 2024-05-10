package com.example.harrypotter.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotter.R
import com.example.harrypotter.databinding.BookListBinding
import com.example.harrypotter.models.BooksModel
import com.example.harrypotter.utils.getBookImageResourceId
import com.example.harrypotter.views.activities.BookDetailsActivity

class BooksAdapter(private val context: Context, private val booksModel: List<BooksModel>) :
    RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    class ViewHolder(private val binding: BookListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, model: BooksModel) {
            binding.apply {
                model.apply {
                    bookTitleTv.text = title
                    bookCoverImg.setImageResource(getBookImageResourceId(id))
                }
                mainCardView.setOnClickListener {
                    val intent = Intent(context, BookDetailsActivity::class.java)
                    intent.putExtra("bookDetails", model)
                    intent.putExtra("bookImage", R.drawable.hp_splash)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            BookListBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context = context, model = booksModel[position])
    }

    override fun getItemCount(): Int = booksModel.size

}