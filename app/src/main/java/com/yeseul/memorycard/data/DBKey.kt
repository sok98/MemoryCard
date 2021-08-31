package com.yeseul.memorycard.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class DBKey {
    companion object {
        const val DB_WORDS = "Words"
        const val USER_ID = "UserId"
        const val WORD = "word"
        const val MEANING = "meaning"
    }
}