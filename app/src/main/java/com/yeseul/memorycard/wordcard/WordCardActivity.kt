package com.yeseul.memorycard.wordcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yeseul.memorycard.DBKey.Companion.DB_WORDS
import com.yeseul.memorycard.DBKey.Companion.MEANING
import com.yeseul.memorycard.DBKey.Companion.USER_ID
import com.yeseul.memorycard.DBKey.Companion.WORD
import com.yeseul.memorycard.databinding.ActivityWordCardBinding
import com.yeseul.memorycard.wordlist.WordListActivity
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom

class WordCardActivity : AppCompatActivity(), CardStackListener {

    private lateinit var binding: ActivityWordCardBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var wordDB: DatabaseReference

    private val manager by lazy {
        CardStackLayoutManager(this, this)
    }
    private val adapter = CardAdapter()
    private val cardItems = mutableListOf<CardItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = auth.currentUser?.uid.orEmpty()
        wordDB = Firebase.database.reference.child(userId).child(DB_WORDS)

        getCardItems()

        initCardStackView()
        initShowWordListButton()


    }

    private fun initCardStackView() {
        val stackView = binding.cardStackView
        manager.setStackFrom(StackFrom.Top)
        stackView.layoutManager = manager
        stackView.adapter = adapter
    }

    private fun initShowWordListButton() {
        binding.showWordListButton.setOnClickListener {
            startActivity(Intent(this, WordListActivity::class.java))
        }
    }

    private fun getCardItems() {
        wordDB.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val word = snapshot.child(WORD).value.toString()
                val meaning = snapshot.child(MEANING).value.toString()

                cardItems.add(CardItem(word, meaning))
                adapter.submitList(cardItems)
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                cardItems.find { it.word == snapshot.key }?.let {
                    it.meaning = snapshot.child(MEANING).value.toString()
                }
                adapter.submitList(cardItems)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    private fun check() {

    }

    private fun uncheck() {

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onCardSwiped(direction: Direction?) {
//        when (direction) {
//            Direction.Right -> check()
//            Direction.Left -> uncheck()
//            else -> { }
//        }
    }

    override fun onCardRewound() {}

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {}

    override fun onCardDisappeared(view: View?, position: Int) {}
}