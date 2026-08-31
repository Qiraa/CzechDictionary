package com.minger.czechdictionary.data

import kotlinx.serialization.Serializable

@Serializable
data class WordResponse(
    val word: String,
    val entries: List<Entry>,
)

@Serializable
data class Entry(
    val partOfSpeech: String,
    val senses: List<Sense>,
)

@Serializable
data class Sense(
    val definition: String,
    val translation: List<Translation>? = null,
)

@Serializable
data class Translation(
    val language: Language,
    val word: String,
)

@Serializable
data class Language(
    val name: String,
)