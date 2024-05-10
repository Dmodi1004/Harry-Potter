package com.example.harrypotter.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.harrypotter.adapters.BooksAdapter
import com.example.harrypotter.database.RoomInstance
import com.example.harrypotter.databinding.FragmentBooksBinding
import com.example.harrypotter.models.BooksModel
import com.example.harrypotter.repository.Repository
import com.example.harrypotter.utils.isNetworkAvailable
import com.example.harrypotter.viewModels.MainViewModel
import com.example.harrypotter.viewModels.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BooksFragment : Fragment() {

    private val binding by lazy {
        FragmentBooksBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: BooksAdapter
    private lateinit var viewModel: MainViewModel

    private var isDataLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        if (!isDataLoaded && isNetworkAvailable(requireContext())) {

            viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
            viewModel.getBooks()
            viewModel.bookResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    val bookList = response.body()
                    bookList?.let { books ->
                        setupRecyclerView(books)
                        insertBooksIntoRoom(books)
                        isDataLoaded = true
                    }
                    binding.retryBtn.visibility = View.GONE
                } else {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                    binding.retryBtn.visibility = View.VISIBLE
                    getAllCachedBooks()
                }
                binding.animationView.visibility = View.GONE
            }
            viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
                binding.retryBtn.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        } else {
            getAllCachedBooks()
        }

        binding.retryBtn.setOnClickListener {
            viewModel.getBooks()
            binding.animationView.visibility = View.VISIBLE
        }

        return binding.root
    }

    private fun setupRecyclerView(books: List<BooksModel>) {
        adapter = BooksAdapter(requireContext(), books)
        binding.booksRv.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.booksRv.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
    }

    private fun insertBooksIntoRoom(books: List<BooksModel>) {
        val roomDatabase = RoomInstance.getInstance(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val booksDao = roomDatabase.booksDao()
            for (book in books) {
                booksDao.insertBooks(book)
            }
        }
    }

    private fun getAllCachedBooks() {

        val context = context ?: return
        val roomDatabase = RoomInstance.getInstance(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val booksDao = roomDatabase.booksDao()
            val cachedBooks = booksDao.getAllBooks()

            withContext(Dispatchers.Main) {
                if (cachedBooks.isEmpty()) {
                    Toast.makeText(
                        context,
                        "No internet connection and no cached data",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    setupRecyclerView(cachedBooks)
                }
            }
        }
        binding.animationView.visibility = View.GONE
    }

}