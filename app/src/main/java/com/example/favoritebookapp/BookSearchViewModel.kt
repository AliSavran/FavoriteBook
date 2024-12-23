package com.example.favoritebookapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class BookSearchViewModel(private val repository: BookRepository) : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {

        searchBooks("fantasy")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val searchResults = repository.searchBooks(query)
                _books.value = searchResults
            } catch (e: Exception) {
                _error.value = e.message ?: "Bir hata oluştu"
                _books.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    class Factory(private val repository: BookRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookSearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BookSearchViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}