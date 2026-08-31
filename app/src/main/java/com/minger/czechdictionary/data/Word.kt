package com.minger.czechdictionary.data

data class Word (
    val word: String,
    val partOfSpeech: String,
    val translate: String,
    val isFavourite: Boolean,
    val definition: String,
    val createdAt: Long,
)