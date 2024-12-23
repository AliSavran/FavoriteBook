package com.example.favoritebookapp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(
    private val database: BookDatabase
) {
    private val openLibraryService = RetrofitClient.instance
    private val favoriteBookDao = database.favoriteBookDao()

    suspend fun searchBooks(query: String): List<Book> {
        return try {
            val response = openLibraryService.searchBooks(query)
            response.books.map { result ->
                Book(
                    id = result.key,
                    title = result.title,
                    authors = result.authors ?: listOf(),
                    coverUrl = result.coverId?.let {
                        "https://covers.openlibrary.org/b/id/$it-M.jpg"
                    }
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao.getAllFavoriteBook().map { favoriteEntities ->
            favoriteEntities.map { entity ->
                Book(
                    id = entity.id,
                    title = entity.title,
                    authors = listOf(entity.author),
                    coverUrl = entity.coverUrl
                )
            }
        }
    }

    suspend fun addToFavorites(book: Book) {
        val favoriteBook = FavoriteBookEntity(
            id = book.id,
            title = book.title,
            author = book.authors.firstOrNull() ?: "",
            coverUrl = book.coverUrl
        )
        favoriteBookDao.insertFavoriteBook(favoriteBook)
    }

    suspend fun removeFromFavorites(book: Book) {
        val favoriteBook = FavoriteBookEntity(
            id = book.id,
            title = book.title,
            author = book.authors.firstOrNull() ?: "",
            coverUrl = book.coverUrl
        )
        favoriteBookDao.removeFavoriteBook(favoriteBook)
    }

    suspend fun isBookFavorite(bookId: String): Boolean {
        return favoriteBookDao.isBookFavorite(bookId)
    }
}