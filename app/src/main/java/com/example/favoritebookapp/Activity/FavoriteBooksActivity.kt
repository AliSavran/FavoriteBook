package com.example.favoritebookapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favoritebookapp.BookAdapter
import com.example.favoritebookapp.BookDatabase
import com.example.favoritebookapp.BookRepository
import com.example.favoritebookapp.FavoriteViewModel
import com.example.favoritebookapp.databinding.ActivityFavoriteBooksBinding
import kotlinx.coroutines.launch

class FavoriteBooksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBooksBinding
    private lateinit var favoriteAdapter: BookAdapter
    private val viewModel:FavoriteViewModel by viewModels {
        FavoriteViewModel.Factory(BookRepository(BookDatabase.getDatabase(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupRecyclerView()

    }

    private fun setupRecyclerView(){

        favoriteAdapter = BookAdapter { book ->
            val intent = Intent(this,BookDetailActivity::class.java).apply {
                putExtra("BOOK_ID", book.id)
                putExtra("BOOK", book)
            }
            startActivity(intent)
        }

        binding.recyclerViewFavorites.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(this@FavoriteBooksActivity)
        }
    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            viewModel.favoriteBooks.collect{ books ->
                favoriteAdapter.submitList(books)

                binding.textEmptyFavorites.visibility =
                    if (books.isEmpty()) View.VISIBLE
                    else View.GONE
            }
        }
    }
}