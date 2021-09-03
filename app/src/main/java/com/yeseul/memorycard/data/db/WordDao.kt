package com.yeseul.memorycard.data.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeseul.memorycard.data.db.entity.Word

interface WordDao {

    @Query("SELECT * FROM wordTable")
    fun getALl(): List<Word>

    @Query("SELECT * FROM wordTable WHERE checked='false'")
    fun getUnchecked(): List<Word>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word)

    @Query("DELETE FROM wordTable WHERE idx=:idx")
    fun deleteWord(idx: Int)

    @Query("UPDATE wordTable set checked= :checked WHERE idx=:idx")
    fun updateCheck(idx: Int, checked: Boolean)

    @Query("UPDATE wordTable set word=:word, meaning=:meaning, description=:description WHERE idx=:idx")
    fun updateAll(idx: Int, word: String, meaning: String, description: String)

}