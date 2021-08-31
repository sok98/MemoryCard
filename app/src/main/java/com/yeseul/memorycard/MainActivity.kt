package com.yeseul.memorycard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.yeseul.memorycard.presentation.wordlist.WordListActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val uniqueID = UUID.randomUUID().toString()
        // TODO UUID 값 datastore에 저장하고 읽어오기

        if(auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, WordListActivity::class.java))
            finish()
        }
    }
}

