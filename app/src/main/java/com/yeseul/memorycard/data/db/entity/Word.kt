package com.yeseul.memorycard.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wordTable")
data class Word(
    val word: String,
    val meaning: String,
    val description: String,
    val checked: Boolean
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}