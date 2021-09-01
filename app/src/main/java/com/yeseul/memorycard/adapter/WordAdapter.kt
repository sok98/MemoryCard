package com.yeseul.memorycard.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yeseul.memorycard.databinding.ItemWordListBinding
import com.yeseul.memorycard.data.WordModel

class WordAdapter : ListAdapter<WordModel, WordAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemWordListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(wordModel: WordModel) {
            val checkbox = binding.checkBox
            checkbox.text = wordModel.word

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Log.d("CHECK", "체크 : ${wordModel.word}")
                } else {
                    Log.d("CHECK", "체크해제 : ${wordModel.word}")
                }
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