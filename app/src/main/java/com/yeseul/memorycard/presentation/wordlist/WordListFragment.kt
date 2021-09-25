package com.yeseul.memorycard.presentation.wordlist

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeseul.memorycard.R
import com.yeseul.memorycard.adapter.WordAdapter
import com.yeseul.memorycard.presentation.wordcard.WordCardFragment
import com.yeseul.memorycard.databinding.FragmentWordListBinding
import com.yeseul.memorycard.presentation.WordViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordListFragment : Fragment(R.layout.fragment_word_list) {

    private lateinit var binding: FragmentWordListBinding
    private lateinit var wordAdapter: WordAdapter
    private val model: WordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWordListBinding.bind(view)

        initSearchButton()
        initShowWordCardButton()
        initAddFloatingButton()
        initRecyclerView()
        getWordList()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.searchEditText.setText("")
            getWordList()
        }
    }


    private fun initSearchButton() {
        // todo 검색 구현
//        binding.searchImageButton.setOnClickListener {
//
//            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//            manager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
//            binding.searchEditText.clearFocus()
//
//            val searchWord = binding.searchEditText.text.toString()
//            model.searchWord(searchWord).observe(this, Observer { wordList ->
//                wordAdapter.submitList(wordList)
//            })
//        }
    }

    private fun initShowWordCardButton() {
        binding.showWordCardButton.setOnClickListener {
            // todo WordCardFragment 로 이동
//            startActivity(Intent(this, WordCardFragment::class.java))
        }
    }

    private fun initAddFloatingButton() {
        binding.addFloatingButton.setOnClickListener {
            startActivity(Intent(activity, AddWordActivity::class.java))
        }
    }

    private fun initRecyclerView() {
        wordAdapter = WordAdapter(onChecked = { word, b ->
            lifecycleScope.launch(Dispatchers.IO){
                model.updateCheck(word.id, b)
            }
        }, onClicked = { word ->
            val intent = Intent(activity, AddWordActivity::class.java)
            intent.putExtra("id", word.id)
            startActivity(intent)
        })
        binding.wordRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.wordRecyclerView.adapter = wordAdapter


    }

    private fun getWordList() {
        model.getAll().observe(viewLifecycleOwner, Observer { wordList ->
            wordAdapter.submitList(wordList)
            wordAdapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        wordAdapter.notifyDataSetChanged()
    }
}