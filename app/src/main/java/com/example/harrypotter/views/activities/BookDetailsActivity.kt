package com.example.harrypotter.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.harrypotter.databinding.ActivityBookDetailsBinding
import com.example.harrypotter.models.BooksModel
import com.example.harrypotter.utils.getBookImageResourceId

class BookDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBookDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bookDetails = intent.getSerializableExtra("bookDetails") as BooksModel

        binding.apply {
            bookDetails.apply {
                bookCoverImage.setImageResource(getBookImageResourceId(bookDetails.id))
                bookTitleTv.text = title
                authorNameTv.text = author
                if (id == 3) {
                    releaseDateTv.text = releseDay
                } else {
                    releaseDateTv.text = releaseDay
                }
                bookDescTv.text = description
            }
        }

    }
}