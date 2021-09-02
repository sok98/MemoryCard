package com.yeseul.memorycard.presentation.wordlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yeseul.memorycard.databinding.ActivityAddWordBinding

class ModifyWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}