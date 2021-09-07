package com.yeseul.memorycard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yeseul.memorycard.data.db.entity.Word
import com.yeseul.memorycard.databinding.ItemWordListBinding

class WordAdapter(val onChecked: (Word, Boolean) -> Unit, val onClicked: (Word) -> Unit)
    : ListAdapter<Word, WordAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemWordListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            val checkbox = binding.checkBox
            checkbox.text = word.word
            checkbox.isChecked = word.checked

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                onChecked(word, isChecked)
            }

            binding.root.setOnClickListener {
                onClicked(word)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWordListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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