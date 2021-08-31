package com.yeseul.memorycard.presentation.wordlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yeseul.memorycard.data.DBKey.Companion.DB_WORDS
import com.yeseul.memorycard.adapter.WordAdapter
import com.yeseul.memorycard.data.DataStoreKey
import com.yeseul.memorycard.data.WordModel
import com.yeseul.memorycard.presentation.wordcard.WordCardActivity
import com.yeseul.memorycard.databinding.ActivityWordListBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WordListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordListBinding
    private val wordList = mutableListOf<WordModel>()
    private lateinit var wordAdapter: WordAdapter

    private lateinit var dataStore: DataStore<Preferences>
    private val dataStoreKey = preferencesKey<String>(DataStoreKey.USER_KEY)
    private lateinit var userId : String
    private lateinit var wordDB : DatabaseReference

    private val listener = object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val wordModel = snapshot.getValue(WordModel::class.java)
            wordModel ?: return

            wordList.add(wordModel)
            wordAdapter.submitList(wordList)
        }
        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wordList.clear()
        wordAdapter = WordAdapter()

        dataStore = createDataStore(DataStoreKey.DATASTORE)
        lifecycleScope.launch {
            read()?.let {
                userId = it
                wordDB = Firebase.database.reference.child(userId).child(DB_WORDS)
                wordDB.addChildEventListener(listener)
            }
        }


        binding.wordRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.wordRecyclerView.adapter = wordAdapter

        initShowWordCardButton()
        initAddFloatingButton()
    }

    private fun initShowWordCardButton() {
        binding.showWordCardButton.setOnClickListener {
            startActivity(Intent(this, WordCardActivity::class.java))
        }
    }

    private fun initAddFloatingButton() {
        binding.addFloatingButton.setOnClickListener {
            startActivity(Intent(this, AddWordActivity::class.java))
        }
    }

    private suspend fun read(): String? {
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    override fun onResume() {
        super.onResume()
        wordAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        wordDB.removeEventListener(listener)
    }

}