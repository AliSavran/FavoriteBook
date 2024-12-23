package com.example.favoritebookapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {
    @Query("SELECT * FROM favorite_books")
    fun getAllFavoriteBook(): Flow<List<FavoriteBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteBook(book: FavoriteBookEntity)

    @Delete
    suspend fun removeFavoriteBook(book: FavoriteBookEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_books WHERE id = :bookId)")
    suspend fun isBookFavorite(bookId: String): Boolean
}