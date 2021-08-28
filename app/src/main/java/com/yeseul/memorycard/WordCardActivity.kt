package com.yeseul.memorycard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeseul.memorycard.databinding.ActivityWordCardBinding

class WordCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.showWordListButton.setOnClickListener {
            startActivity(Intent(this, WordListActivity::class.java))
        }
    }
}