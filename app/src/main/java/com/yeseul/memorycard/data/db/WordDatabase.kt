package com.yeseul.memorycard.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yeseul.memorycard.data.db.entity.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        private var instance: WordDatabase? = null

        @Synchronized
        fun getInstance(context: Context): WordDatabase? {
            if (instance == null) {
                synchronized(WordDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordDatabase::class.java,
                        "word-database"
                    ).build()
                }
            }
            return instance
        }
    }
}