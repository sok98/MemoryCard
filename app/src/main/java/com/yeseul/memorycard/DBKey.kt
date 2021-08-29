package com.yeseul.memorycard

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DBKey {
    companion object {
        const val DB_WORDS = "Words"
        const val USER_ID = "UserId"
        const val WORD = "word"
        const val MEANING = "meaning"
    }
}