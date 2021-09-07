package com.yeseul.memorycard.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.yeseul.memorycard.data.db.WordDao
import com.yeseul.memorycard.data.db.WordDatabase
import com.yeseul.memorycard.data.db.entity.Word

class WordRepository(application: Application) {
    private val wordDao: WordDao
    private val wordList: LiveData<List<Word>>

    init {
        var db = WordDatabase.getInstance(application)
        wordDao = db!!.wordDao()
        wordList = db.wordDao().getALl()
    }

    fun getAll() : LiveData<List<Word>> {
        return wordDao.getALl()
    }

    fun getOne(id: Int) : Word {
        return wordDao.getOne(id)
    }

    fun getUnchecked() : LiveData<List<Word>> {
        return wordDao.getUnchecked()
    }

    fun insertWord(word: Word) {
        wordDao.insertWord(word)
    }

    fun deleteWord(id: Int) {
        wordDao.deleteWord(id)
    }

    fun updateWord(id: Int, word: String, meaning: String, description: String) {
        wordDao.updateWord(id, word, meaning, description)
    }

    fun updateCheck(id: Int, checked: Boolean) {
        wordDao.updateCheck(id, checked)
    }
}