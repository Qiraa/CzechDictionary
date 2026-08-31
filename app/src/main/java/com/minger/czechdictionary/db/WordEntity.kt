package com.minger.czechdictionary.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.minger.czechdictionary.data.Word

@Entity(tableName = "words")
class WordEntity(
    @PrimaryKey val word: String,
    val partOfSpeech: String,
    val translate: String,
    val isFavourite: Boolean,
    val definition: String,
    val createdAt: Long,
) {
    val hasData: Boolean
        get() = definition.isNotEmpty() || translate.isNotEmpty()
}

fun WordEntity.toWord(): Word {
    return Word(
        word,
        partOfSpeech,
        translate,
        isFavourite,
        definition,
        createdAt,
    )
}

fun Word.toWordEntity(): WordEntity {
    return WordEntity(
        word,
        partOfSpeech,
        translate,
        isFavourite,
        definition,
        createdAt,
    )
}