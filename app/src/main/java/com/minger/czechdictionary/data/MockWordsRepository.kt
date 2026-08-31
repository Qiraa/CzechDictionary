package com.minger.czechdictionary.data

import com.minger.czechdictionary.db.WordsDao
import com.minger.czechdictionary.db.toWord
import com.minger.czechdictionary.db.toWordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.withContext

class MockWordsRepository(
    private val apiService: ApiService,
    private val wordsDao: WordsDao,
) : WordsRepository {

    override suspend fun getWords(): List<Word> {
        return withContext(Dispatchers.IO) {
            wordsDao.getWords().map { it.toWord() }
        }
    }

    override fun observeWords(): Flow<List<Word>> {
        return wordsDao.observeWords().map { entities -> entities.map { it.toWord() } }
    }

    override suspend fun getWord(word: String): Word {
        val cached = withContext(Dispatchers.IO) {
            wordsDao.getWord(word)
                ?.takeIf { it.hasData }
                ?.toWord()
        }
        if (cached != null) return cached

        val firstEntry = apiService.getWord(word).entries.first()
        val firstSense = firstEntry.senses.first()
        val word = Word(
            word = word,
            partOfSpeech = firstEntry.partOfSpeech,
            translate = firstSense.translation?.firstOrNull { it.language.name == "en" }?.word.orEmpty(),
            isFavourite = false,
            definition = firstSense.definition,
            createdAt = System.currentTimeMillis()
        )
        withContext(Dispatchers.IO) {
            wordsDao.addWords(word.toWordEntity())
        }
        return word
    }

    override suspend fun addWord(word: Word) {
        withContext(Dispatchers.IO) {
            wordsDao.addWords(word.toWordEntity())
        }
    }

    override suspend fun deleteWord(word: Word) {
        withContext(Dispatchers.IO) {
            wordsDao.deleteWords(word.toWordEntity())
        }
    }

    override suspend fun updateWord(word: Word) {
        withContext(Dispatchers.IO) {
            wordsDao.addWords(word.toWordEntity())
        }
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            wordsDao.deleteAll()
        }
    }

    override fun observeWord(word: String): Flow<Word> {
        return wordsDao.observeWord(word).filterNotNull().map { it.toWord() }
    }
}