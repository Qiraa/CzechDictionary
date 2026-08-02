package com.minger.czechdictionary.data

class MockWordsRepository : WordsRepository {

    private val words: MutableList<Word> = mutableListOf()

    override suspend fun getWords(): List<Word> = words

    override suspend fun addWord(word: Word) {
        words.add(word)
    }

    override suspend fun deleteWord(word: Word) {
        words.remove(word)
    }

    override suspend fun updateWord(word: Word) {
        words.removeIf { w -> w.word == word.word }
        words.add(word)
    }

    override suspend fun clear() {
        words.clear()
    }
}