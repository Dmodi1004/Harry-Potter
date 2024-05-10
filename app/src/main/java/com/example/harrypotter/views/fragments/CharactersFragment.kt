package com.example.harrypotter.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.harrypotter.adapters.CharactersAdapter
import com.example.harrypotter.database.RoomInstance
import com.example.harrypotter.databinding.FragmentCharactersBinding
import com.example.harrypotter.models.CharactersModel
import com.example.harrypotter.repository.Repository
import com.example.harrypotter.utils.isNetworkAvailable
import com.example.harrypotter.viewModels.MainViewModel
import com.example.harrypotter.viewModels.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersFragment : Fragment() {

    private val binding by lazy {
        FragmentCharactersBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: CharactersAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        if (isNetworkAvailable(requireContext())) {

            viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
            viewModel.getCharacters()
            viewModel.characterResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    val characterList = response.body()
                    characterList?.let { characters ->
                        setupRecyclerView(characters)
                        insertCharactersIntoRoom(characters)
                    }
                    binding.retryBtn.visibility = View.GONE
                } else {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                    binding.retryBtn.visibility = View.VISIBLE
                    getAllCachedCharacters()
                }
                binding.animationView.visibility = View.GONE
            }

            viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
                binding.retryBtn.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        } else {
            // Network not available
            getAllCachedCharacters()
        }

        binding.retryBtn.setOnClickListener {
            viewModel.getCharacters()
        }

        return binding.root
    }

    private fun setupRecyclerView(characters: List<CharactersModel>) {
        adapter = CharactersAdapter(requireContext(), characters)
        binding.charactersRv.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.charactersRv.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
    }

    private fun insertCharactersIntoRoom(characters: List<CharactersModel>) {
        val roomDatabase = RoomInstance.getInstance(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val charactersDao = roomDatabase.charactersDao()
            for (character in characters) {
                charactersDao.insertCharacters(character)
            }
        }
    }

    private fun getAllCachedCharacters() {

        val context = context ?: return
        val roomDatabase = RoomInstance.getInstance(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val charactersDao = roomDatabase.charactersDao()
            val cachedCharacters = charactersDao.getAllCharacters()

            withContext(Dispatchers.Main) {
                if (cachedCharacters.isEmpty()) {
                    Toast.makeText(
                        context,
                        "No internet connection and no cached data",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    setupRecyclerView(cachedCharacters)
                }
            }
        }
        binding.animationView.visibility = View.GONE
    }

}