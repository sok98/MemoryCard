package com.yeseul.memorycard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yeseul.memorycard.databinding.ItemWordListBinding
import com.yeseul.memorycard.data.db.entity.WordModel

class WordAdapter(val onChecked: (WordModel, Boolean) -> Unit, val onClicked: (WordModel) -> Unit) : ListAdapter<WordModel, WordAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemWordListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(wordModel: WordModel) {
            val checkbox = binding.checkBox
            checkbox.text = wordModel.word

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onChecked(wordModel, true)
                } else {
                    onChecked(wordModel, false)
                }
            }

            binding.root.setOnClickListener {
                onClicked(wordModel)
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