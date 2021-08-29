package com.yeseul.memorycard.wordlist

data class WordModel(
    val word: String,
    val meaning: String,
    val description: String,
    val check: Boolean
) {
    constructor(): this("", "", "", false)
}
