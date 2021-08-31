package com.yeseul.memorycard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yeseul.memorycard.databinding.ItemWordListBinding
import com.yeseul.memorycard.data.WordModel

class WordAdapter : ListAdapter<WordModel, WordAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemWordListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(wordModel: WordModel) {
            binding.wordTextView.text = wordModel.word
            val checkbox = binding.checkBox
            checkbox.setOnClickListener {
                checkbox.toggle()
//                binding.checkBox.isChecked = !checkbox.isChecked
            }
            binding.root.setOnClickListener {
                // Todo 단어 상세 페이지로 이동
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
        val diffUtil = object : DiffUtil.ItemCallback<WordModel>() {
            override fun areItemsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
                return oldItem.word == newItem.word
            }

            override fun areContentsTheSame(oldItem: WordModel, newItem: WordModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}