package com.example.harrypotter.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.harrypotter.adapters.InfoAdapter
import com.example.harrypotter.database.RoomInstance
import com.example.harrypotter.databinding.FragmentInfoBinding
import com.example.harrypotter.models.InfoModel
import com.example.harrypotter.repository.Repository
import com.example.harrypotter.utils.isNetworkAvailable
import com.example.harrypotter.viewModels.MainViewModel
import com.example.harrypotter.viewModels.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoFragment : Fragment() {

    private val binding by lazy {
        FragmentInfoBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: InfoAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        if (isNetworkAvailable(requireContext())) {

            viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
            viewModel.getInfo()
            viewModel.infoResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    val infoList = response.body()
                    infoList?.let { info ->
                        setupRecyclerView(info)
                        insertInfoIntoRoom(info)
                    }
                    binding.retryBtn.visibility = View.GONE
                } else {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                    binding.retryBtn.visibility = View.VISIBLE
                    getAllCachedInfo()
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
            getAllCachedInfo()
        }

        binding.retryBtn.setOnClickListener {
            viewModel.getInfo()
        }

        return binding.root
    }

    private fun setupRecyclerView(info: List<InfoModel>) {
        adapter = InfoAdapter(requireContext(), info)
        binding.infoRv.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.infoRv.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
    }

    private fun insertInfoIntoRoom(infoModel: List<InfoModel>) {
        val roomDatabase = RoomInstance.getInstance(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val infoDao = roomDatabase.infoDao()
            for (info in infoModel) {
                infoDao.insertInfo(info)
            }
        }
    }

    private fun getAllCachedInfo() {

        val context = context ?: return
        val roomDatabase = RoomInstance.getInstance(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val infoDao = roomDatabase.infoDao()
            val cachedInfo = infoDao.getAllInfo()

            withContext(Dispatchers.Main) {
                if (cachedInfo.isEmpty()) {
                    Toast.makeText(
                        context,
                        "No internet connection and no cached data",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    setupRecyclerView(cachedInfo)
                }
            }
        }
        binding.animationView.visibility = View.GONE
    }

}