package com.yeseul.memorycard.data.db

import androidx.room.*
import com.yeseul.memorycard.data.db.entity.Word

@Dao
interface WordDao {

    @Query("SELECT * FROM wordTable")
    fun getALl(): List<Word>

    @Query("SELECT * FROM wordTable WHERE checked='false'")
    fun getUnchecked(): List<Word>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word)

    @Query("DELETE FROM wordTable WHERE id=:id")
    fun deleteWord(id: Int)

    @Query("UPDATE wordTable set checked= :checked WHERE id=:id")
    fun updateCheck(id: Int, checked: Boolean)

    @Update
    fun updateAll(word: Word)

//    @Query("UPDATE wordTable set word=:word, meaning=:meaning, description=:description WHERE id=:id")
//    fun updateAll(id: Int, word: String, meaning: String, description: String)

}