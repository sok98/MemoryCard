package com.yeseul.memorycard.presentation.wordlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yeseul.memorycard.data.DBKey.Companion.DB_WORDS
import com.yeseul.memorycard.data.DataStoreKey
import com.yeseul.memorycard.data.WordModel
import com.yeseul.memorycard.databinding.ActivityAddWordBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class AddWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWordBinding
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var userId : String
    private lateinit var wordDB : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStore = createDataStore(DataStoreKey.DATASTORE)
        lifecycleScope.launch {
            read()?.let {
                userId = it
                wordDB = Firebase.database.reference.child(userId).child(DB_WORDS)
            }
        }

        binding.updateButton.setOnClickListener {
            showProgress()
            val word = binding.wordEditText.text.toString()
            val meaning = binding.meaningEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()

            uploadWord(word, meaning, description)
        }
    }

    private suspend fun read(): String? {
        val dataStoreKey = preferencesKey<String>(DataStoreKey.USER_KEY)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    private fun uploadWord(word: String, meaning: String, description: String) {
        val model = WordModel(word, meaning, description, false)
        wordDB.push().setValue(model)
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