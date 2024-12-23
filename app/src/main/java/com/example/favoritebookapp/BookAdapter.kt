package com.example.favoritebookapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favoritebookapp.databinding.ItemBookBinding

class BookAdapter(private val onItemClick: (Book) -> Unit): ListAdapter<Book,BookAdapter.BookViewHolder>(BookDiffCallback()) {
    class BookViewHolder(private val binding : ItemBookBinding,private val onItemClick : (Book) -> Unit):
    RecyclerView.ViewHolder(binding.root){

        fun bind(book: Book){
            binding.textBookTitle.text = book.title
            binding.textBookAuthor.text = book.authors.firstOrNull() ?: "Bilinmeyen Yazar"

            Glide.with(binding.root.context).load(book.coverUrl).into(binding.imageBookCover)

            binding.root.setOnClickListener {
                onItemClick(book)
            }
        }

    }

    class BookDiffCallback : DiffUtil.ItemCallback<Book>() {

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BookViewHolder(binding,onItemClick)
    }

    override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

