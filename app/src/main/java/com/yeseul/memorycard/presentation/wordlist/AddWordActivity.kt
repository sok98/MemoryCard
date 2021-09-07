package com.yeseul.memorycard.presentation.wordlist

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.yeseul.memorycard.data.db.WordDatabase
import com.yeseul.memorycard.data.db.entity.Word
import com.yeseul.memorycard.databinding.ActivityAddWordBinding
import com.yeseul.memorycard.presentation.WordViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWordBinding
    private val model: WordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        if (id == 0) {
            initBlackTextView()
        } else {
            initGrayTextView(id)
        }

        binding.modifyButton.setOnClickListener {
            initBlackTextView()
        }

        binding.deleteButton.setOnClickListener {
            deleteWord(id)
        }

        binding.updateButton.setOnClickListener {
            showProgress()
            val word = binding.wordEditText.text.toString()
            val meaning = binding.meaningEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()

            if (id == 0){
                uploadWord(word, meaning, description)
            } else {
                updateWord(id, word, meaning, description)
            }
        }
    }

    private fun initGrayTextView(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            val setWord = model.getOne(id)

            binding.wordEditText.let {
                it.setText(setWord.word)
                it.setTextColor(Color.GRAY)
                it.isEnabled = false
            }
            binding.meaningEditText.let {
                it.setText(setWord.meaning)
                it.setTextColor(Color.GRAY)
                it.isEnabled = false
            }
            binding.descriptionEditText.let {
                it.setText(setWord.description)
                it.setTextColor(Color.GRAY)
                it.isEnabled = false
            }
        }
        binding.updateButton.isVisible = false
        binding.deleteButton.isVisible = true
        binding.modifyButton.isVisible = true
    }

    private fun initBlackTextView() {
        binding.wordEditText.let {
            it.setTextColor(Color.BLACK)
            it.isEnabled = true
        }
        binding.meaningEditText.let {
            it.setTextColor(Color.BLACK)
            it.isEnabled = true
        }
        binding.descriptionEditText.let {
            it.setTextColor(Color.BLACK)
            it.isEnabled = true
        }
        binding.updateButton.isVisible = true
        binding.deleteButton.isVisible = false
        binding.modifyButton.isVisible = false
    }

    private fun deleteWord(id: Int) {
        Thread(Runnable {
            model.deleteWord(id)
        }).start()
        hideProgress()
        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun uploadWord(word: String, meaning: String, description: String) {
        val changeWord = Word(word, meaning, description, false)
        Thread(Runnable {
            model.insertWord(changeWord)
        }).start()
        hideProgress()
        finish()
    }

    private fun updateWord(id: Int, word: String, meaning: String, description: String) {
        Thread(Runnable {
            model.updateWord(id, word, meaning, description)
        }).start()
        hideProgress()
        finish()
    }

    private fun showProgress() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgress() {
        binding.progressBar.isVisible = false
    }
}