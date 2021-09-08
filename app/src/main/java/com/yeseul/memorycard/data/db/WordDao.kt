package com.yeseul.memorycard.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yeseul.memorycard.data.db.entity.Word

@Dao
interface WordDao {

    @Query("SELECT * FROM wordTable")
    fun getALl(): LiveData<List<Word>>

    @Query("SELECT * FROM wordTable WHERE id=:id")
    fun getOne(id: Int): Word

    @Query("SELECT * FROM wordTable WHERE checked='false'")
    fun getUnchecked(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word)

    @Query("DELETE FROM wordTable WHERE id=:id")
    fun deleteWord(id: Int)

    @Query("UPDATE wordTable set word=:word, meaning=:meaning, description=:description WHERE id=:id")
    fun updateWord(id: Int, word: String, meaning: String, description: String)

    @Query("UPDATE wordTable set checked= :checked WHERE id=:id")
    fun updateCheck(id: Int, checked: Boolean)

    @Query("SELECT * FROM wordTable WHERE (word LIKE :keyword) OR (meaning LIKE :keyword) OR (description LIKE :keyword)")
    fun searchWord(keyword: String) : LiveData<List<Word>>

}