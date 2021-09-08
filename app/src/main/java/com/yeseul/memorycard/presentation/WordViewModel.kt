package com.yeseul.memorycard.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.yeseul.memorycard.data.db.entity.Word
import com.yeseul.memorycard.data.repository.WordRepository

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = WordRepository(application)

    fun getAll() : LiveData<List<Word>> {
        return repository.getAll()
    }

    fun getOne(id: Int) : Word {
        return repository.getOne(id)
    }

    fun getUnchecked() : LiveData<List<Word>> {
        return repository.getUnchecked()
    }

    fun insertWord(word: Word) {
        repository.insertWord(word)
    }

    fun deleteWord(id: Int) {
        repository.deleteWord(id)
    }

    fun updateCheck(id: Int, checked: Boolean) {
        repository.updateCheck(id, checked)
    }

    fun updateWord(id: Int, word: String, meaning: String, description: String) {
        repository.updateWord(id, word, meaning, description)
    }

    fun searchWord(word: String) : LiveData<List<Word>> {
        return repository.searchWord(word)
    }
}