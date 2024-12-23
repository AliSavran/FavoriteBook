package com.example.favoritebookapp.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.favoritebookapp.Book
import com.example.favoritebookapp.BookDatabase
import com.example.favoritebookapp.BookDetailViewModel
import com.example.favoritebookapp.BookRepository
import com.example.favoritebookapp.R
import com.example.favoritebookapp.databinding.ActivityBookDetailBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding
    private val viewModel: BookDetailViewModel by viewModels {
        BookDetailViewModel.Factory(BookRepository(BookDatabase.getDatabase(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookId = intent.getStringExtra("BOOK_ID")
        val book = intent.getParcelableExtra<Book>("BOOK")

        if (bookId == null || book == null) {
            Toast.makeText(this, "Kitap detayları yüklenemedi", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.loadBookDetails(bookId, book)
        setupFavoriteButton(book)
        observeViewModel()

    }

    private fun setupFavoriteButton(book: Book) {
        lifecycleScope.launch {
            viewModel.isFavorite.collect { isFavorite ->

                binding.fabFavorite.setImageResource(
                    if (isFavorite) R.drawable.heart_outline_filled
                    else R.drawable.heart_filled
                )
            }
        }

        binding.fabFavorite.setOnClickListener {
            viewModel.toggleFavoriteStatus(book)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.book.collect { bookDetails ->
                bookDetails?.let {
                    binding.textBookTitle.text = it.title
                    binding.textBookAuthor.text = it.authors.joinToString()
                    Glide.with(this@BookDetailActivity)
                        .load(it.coverUrl)
                        .into(binding.imageBookCover)
                }
            }
        }
    }
}