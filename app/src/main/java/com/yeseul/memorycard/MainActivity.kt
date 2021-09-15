package com.yeseul.memorycard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeseul.memorycard.presentation.wordlist.WordListActivity
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, WordListActivity::class.java))
    }
}
