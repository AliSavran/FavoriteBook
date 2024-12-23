package com.example.favoritebookapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(private val repository: BookRepository) : ViewModel() {

    private val _book = MutableStateFlow<Book?>(null)
    val book = _book.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun loadBookDetails(bookId: String, book: Book) {
        _book.value = book
        checkFavoriteStatus(bookId)
    }

    private fun checkFavoriteStatus(bookId: String) {
        viewModelScope.launch {
            _isFavorite.value = repository.isBookFavorite(bookId)
        }
    }

    fun toggleFavoriteStatus(book: Book) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                repository.removeFromFavorites(book)
                _isFavorite.value = false
            } else {
                repository.addToFavorites(book)
                _isFavorite.value = true
            }
        }
    }

    class Factory(private val repository: BookRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookDetailViewModel(repository) as T
        }
    }
}