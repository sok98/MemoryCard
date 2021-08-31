package com.yeseul.memorycard.presentation.wordlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yeseul.memorycard.data.DBKey.Companion.DB_WORDS
import com.yeseul.memorycard.adapter.WordAdapter
import com.yeseul.memorycard.data.WordModel
import com.yeseul.memorycard.presentation.wordcard.WordCardActivity
import com.yeseul.memorycard.databinding.ActivityWordListBinding

class WordListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordListBinding
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val wordList = mutableListOf<WordModel>()
    private lateinit var wordAdapter: WordAdapter

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

        val userId = auth.currentUser?.uid.orEmpty()
        wordDB = Firebase.database.reference.child(userId).child(DB_WORDS)
        wordDB.addChildEventListener(listener)

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

    override fun onResume() {
        super.onResume()
        wordAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        wordDB.removeEventListener(listener)
    }

}