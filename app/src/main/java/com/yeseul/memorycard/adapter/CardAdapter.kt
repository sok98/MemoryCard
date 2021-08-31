package com.yeseul.memorycard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yeseul.memorycard.databinding.ItemWordCardBinding
import com.yeseul.memorycard.data.CardItem

class CardAdapter : ListAdapter<CardItem, CardAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemWordCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cardItem: CardItem) {
            binding.wordTextView.text = cardItem.word
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWordCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CardItem>() {
            override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return oldItem.word == newItem.word
            }

            override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}