package com.yeseul.memorycard.presentation.wordlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeseul.memorycard.adapter.WordAdapter
import com.yeseul.memorycard.data.db.WordDatabase
import com.yeseul.memorycard.data.db.entity.Word
import com.yeseul.memorycard.presentation.wordcard.WordCardActivity
import com.yeseul.memorycard.databinding.ActivityWordListBinding
import com.yeseul.memorycard.presentation.WordViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWordListBinding
    private lateinit var wordAdapter: WordAdapter
    private val model: WordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initShowWordCardButton()
        initAddFloatingButton()
        initRecyclerView()
        getWordList()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            getWordList()
        }
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

    private fun initRecyclerView() {
        wordAdapter = WordAdapter(onChecked = { word, b ->
            lifecycleScope.launch(Dispatchers.IO){
                model.updateCheck(word.id, b)
            }
        }, onClicked = { word ->
            // todo 수정 페이지로 이동
            val intent = Intent(this, AddWordActivity::class.java)
            intent.putExtra("id", word.id)
            startActivity(intent)
        })
        binding.wordRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.wordRecyclerView.adapter = wordAdapter


    }

    private fun getWordList() {
        model.getAll().observe(this, Observer { wordList ->
            wordAdapter.submitList(wordList)
            wordAdapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        wordAdapter.notifyDataSetChanged()
    }
}