package com.yeseul.memorycard.presentation.wordcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.yeseul.memorycard.adapter.CardAdapter
import com.yeseul.memorycard.data.db.entity.Word
import com.yeseul.memorycard.databinding.ActivityWordCardBinding
import com.yeseul.memorycard.presentation.WordViewModel
import com.yeseul.memorycard.presentation.wordlist.WordListActivity
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordCardActivity : AppCompatActivity(), CardStackListener {

    private lateinit var binding: ActivityWordCardBinding
    private val manager by lazy {
        CardStackLayoutManager(this, this)
    }
    private val adapter = CardAdapter()
    private val model: WordViewModel by viewModels()
    private val cardItems = mutableListOf<Word>()
    private var cardId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initReloadCardButton()
        initShowWordListButton()
        initCardStackView()
        loadCard()

    }

    private fun initReloadCardButton() {
        binding.reloadCardButton.setOnClickListener {
            cardId = 0
            loadCard()
        }
    }

    private fun initShowWordListButton() {
        binding.showWordListButton.setOnClickListener {
            startActivity(Intent(this, WordListActivity::class.java))
        }
    }

    private fun initCardStackView() {
        val stackView = binding.cardStackView
        manager.setStackFrom(StackFrom.Top)
        stackView.layoutManager = manager
        stackView.adapter = adapter
    }

    private fun loadCard() {
        model.getAll().observe(this, Observer { wordList ->
            cardItems.clear()
            wordList.forEach { word ->
                if (word.id > cardId){
                    cardItems.add(word)
                }
            }
            adapter.submitList(cardItems)
            adapter.notifyDataSetChanged()
        })
    }

    private fun check(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            model.updateCheck(id, true)
        }
    }

    private fun uncheck(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            model.updateCheck(id, false)
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onCardSwiped(direction: Direction?) {
        cardId = cardItems[manager.topPosition - 1].id
        cardItems.removeFirst()
        when (direction) {
            Direction.Right -> check(cardId)
            Direction.Left -> uncheck(cardId)
            else -> { }
        }
    }

    override fun onCardRewound() {}

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {}

    override fun onCardDisappeared(view: View?, position: Int) {}
}