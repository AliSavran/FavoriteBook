package com.example.favoritebookapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteBookEntity::class], version = 1)
abstract class BookDatabase : RoomDatabase(){

    abstract fun favoriteBookDao(): FavoriteBookDao

    companion object{
        @Volatile
        private var INSTANCE : BookDatabase? = null

        fun getDatabase(context: Context) : BookDatabase{
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}