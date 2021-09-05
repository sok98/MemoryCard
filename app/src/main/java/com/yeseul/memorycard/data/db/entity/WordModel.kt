package com.yeseul.memorycard.data.db.entity

data class WordModel(
    val word: String,
    val meaning: String,
    val description: String,
    val check: Boolean
) {
    constructor(): this("", "", "", false)
}
