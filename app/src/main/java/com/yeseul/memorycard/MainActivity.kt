package com.yeseul.memorycard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yeseul.memorycard.data.DBKey.Companion.USER_ID
import com.yeseul.memorycard.data.DataStoreKey.Companion.DATASTORE
import com.yeseul.memorycard.data.DataStoreKey.Companion.USER_KEY
import com.yeseul.memorycard.presentation.wordlist.WordListActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var dataStore: DataStore<Preferences>
    private val dataStoreKey = preferencesKey<String>(USER_KEY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        dataStore = createDataStore(DATASTORE)
        lifecycleScope.launch {
            if (read() == null) {
                val uniqueID = UUID.randomUUID().toString()
                save(uniqueID)
            }
            read()?.let {
                makeDatabase(it)
            }
        }

        startActivity(Intent(this, WordListActivity::class.java))
        finish()
    }

    private suspend fun save(value: String) {
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    private suspend fun read(): String? {
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    private fun makeDatabase(userId: String) {
        val currentUserDB = Firebase.database.reference.child(userId)
        val user = mutableMapOf<String, Any>()
        user[USER_ID] = userId
        currentUserDB.updateChildren(user)
        finish()
    }
}

