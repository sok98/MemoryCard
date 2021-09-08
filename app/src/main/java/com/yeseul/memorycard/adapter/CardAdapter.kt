package com.yeseul.memorycard.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yeseul.memorycard.R
import com.yeseul.memorycard.databinding.ItemWordCardBinding
import com.yeseul.memorycard.data.db.entity.Word

class CardAdapter : ListAdapter<Word, CardAdapter.ViewHolder>(diffUtil) {

    private var tag = true

    inner class ViewHolder(private val binding: ItemWordCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.wordTextView.text = word.word
            initWordTextView(binding)

            binding.root.setOnClickListener {
                if (tag){
                    binding.wordTextView.text = word.meaning
                    meaningWordTextView(binding)

                } else {
                    binding.wordTextView.text = word.word
                    initWordTextView(binding)

                }
            }
        }
    }

    private fun initWordTextView(binding: ItemWordCardBinding) {
        binding.wordTextView.setTextColor(Color.BLACK)
        binding.wordLinearLayout.setBackgroundResource(R.drawable.background_card)
        tag = true

    }

    private fun meaningWordTextView(binding: ItemWordCardBinding) {
        binding.wordTextView.setTextColor(Color.WHITE)
        binding.wordLinearLayout.setBackgroundResource(R.drawable.background_card_meaning)
        tag = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWordCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem == newItem
            }

        }
    }
}