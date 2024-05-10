package com.example.harrypotter.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harrypotter.models.BooksModel
import com.example.harrypotter.models.CharactersModel
import com.example.harrypotter.models.InfoModel
import com.example.harrypotter.models.SpellsModel
import com.example.harrypotter.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val bookResponse: MutableLiveData<Response<List<BooksModel>>> = MutableLiveData()
    val characterResponse: MutableLiveData<Response<List<CharactersModel>>> = MutableLiveData()
    val spellResponse: MutableLiveData<Response<List<SpellsModel>>> = MutableLiveData()
    val infoResponse: MutableLiveData<Response<List<InfoModel>>> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun getBooks() {
        viewModelScope.launch {
            try {
                val response = repository.getBooks()
                bookResponse.value = response
            } catch (e: Exception) {
                errorMessage.value =
                    "Request timed out. Please check your internet connection and try again."
            }
        }
    }

    fun getCharacters() {
        viewModelScope.launch {
            try {
                val response = repository.getCharacters()
                characterResponse.value = response
            } catch (e: Exception) {
                errorMessage.value =
                    "Request timed out. Please check your internet connection and try again."
            }
        }
    }

    fun getSpells() {
        viewModelScope.launch {
            try {
                val response = repository.getSpells()
                spellResponse.value = response
            } catch (e: Exception) {
                errorMessage.value =
                    "Request timed out. Please check your internet connection and try again."
            }
        }
    }

    fun getInfo() {
        viewModelScope.launch {
            try {
                val response = repository.getInfo()
                infoResponse.value = response
            } catch (e: Exception) {
                errorMessage.value =
                    "Request timed out. Please check your internet connection and try again."
            }
        }
    }

}