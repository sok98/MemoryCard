package com.yeseul.memorycard.presentation.wordcard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.yeseul.memorycard.adapter.CardAdapter
import com.yeseul.memorycard.data.db.entity.Word
import com.yeseul.memorycard.databinding.FragmentWordCardBinding
import com.yeseul.memorycard.presentation.WordViewModel
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordCardFragment : Fragment(), CardStackListener {

    private lateinit var binding: FragmentWordCardBinding
    private val manager by lazy {
        CardStackLayoutManager(activity, this)
    }
    private val adapter = CardAdapter()
    private val model: WordViewModel by viewModels()
    private val cardItems = mutableListOf<Word>()
    private var cardId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWordCardBinding.bind(view)

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
            // todo WordList 로 전환 (fragment -> fragment)
//            startActivity(Intent(this, WordListActivity::class.java))
        }
    }

    private fun initCardStackView() {
        val stackView = binding.cardStackView
        manager.setStackFrom(StackFrom.Top)
        stackView.layoutManager = manager
        stackView.adapter = adapter
    }

    private fun loadCard() {
        model.getAll().observe(viewLifecycleOwner, Observer { wordList ->
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