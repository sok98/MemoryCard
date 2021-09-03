package com.yeseul.memorycard.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wordTable")
data class Word(
    @PrimaryKey val idx: Int,
    val word: String,
    val meaning: String,
    val description: String,
    val checked: Boolean
)