package com.example.harrypotter.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.harrypotter.adapters.SpellsAdapter
import com.example.harrypotter.database.RoomInstance
import com.example.harrypotter.databinding.FragmentSpellBinding
import com.example.harrypotter.models.SpellsModel
import com.example.harrypotter.repository.Repository
import com.example.harrypotter.utils.isNetworkAvailable
import com.example.harrypotter.viewModels.MainViewModel
import com.example.harrypotter.viewModels.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpellFragment : Fragment() {

    private val binding by lazy {
        FragmentSpellBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: SpellsAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        if (isNetworkAvailable(requireContext())) {

            viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
            viewModel.getSpells()
            viewModel.spellResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    val spellList = response.body()
                    spellList?.let { spells ->
                        setupRecyclerView(spells)
                        insertSpellsIntoRoom(spells)
                    }
                    binding.retryBtn.visibility = View.GONE
                } else {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                    binding.retryBtn.visibility = View.VISIBLE
                    getAllCachedSpells()
                }
                binding.animationView.visibility = View.GONE
            }

            viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
                binding.retryBtn.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        } else {
            // Network is not available
            getAllCachedSpells()
        }

        binding.retryBtn.setOnClickListener {
            viewModel.getSpells()
        }

        return binding.root
    }

    private fun setupRecyclerView(spells: List<SpellsModel>) {
        adapter = SpellsAdapter(requireContext(), spells)
        binding.spellsRv.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.spellsRv.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
    }

    private fun insertSpellsIntoRoom(spells: List<SpellsModel>) {
        val roomDatabase = RoomInstance.getInstance(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val spellsDao = roomDatabase.spellsDao()
            for (spell in spells) {
                spellsDao.insertSpells(spell)
            }
        }
    }

    private fun getAllCachedSpells() {

        val context = context ?: return
        val roomDatabase = RoomInstance.getInstance(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val spellDao = roomDatabase.spellsDao()
            val cachedSpells = spellDao.getAllSpells()

            withContext(Dispatchers.Main) {
                if (cachedSpells.isEmpty()) {
                    Toast.makeText(
                        context,
                        "No internet connection and no cached data",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    setupRecyclerView(cachedSpells)
                }
            }
        }
        binding.animationView.visibility = View.GONE
    }

}