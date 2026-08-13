package com.minger.czechdictionary.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minger.czechdictionary.data.Word
import com.minger.czechdictionary.domain.AddWordUseCase
import com.minger.czechdictionary.domain.ClearWordsUseCase
import com.minger.czechdictionary.domain.GetWordsUseCase
import com.minger.czechdictionary.domain.UpdateWordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val getWordsUseCase: GetWordsUseCase,
    private val addWordUseCase: AddWordUseCase,
    private val clearWordsUseCase: ClearWordsUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
) : ViewModel() {

    private val mutableState: MutableStateFlow<HistoryState> =
        MutableStateFlow(HistoryState.Loading)

    val state: StateFlow<HistoryState> = mutableState.asStateFlow()

    private val currentSearchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = currentSearchQuery.asStateFlow()

    init {
        observeWords()
    }

    private fun observeWords() {
        viewModelScope.launch {
            getWordsUseCase.observeWords().collect { words ->
                applyFilter(words, currentSearchQuery.value)
            }
        }
    }

    private fun applyFilter(words: List<Word>, query: String) {
        val filtered = if (query.isBlank()) {
            words
        } else {
            words.filter { it.word.contains(query, ignoreCase = true) }
        }
        mutableState.value = HistoryState.Success(
            filtered.map { word ->
                WordItem(
                    word = word.word,
                    isFavourite = word.isFavourite,
                )
            }
        )
    }

    fun onSearchQueryChanged(query: String) {
        currentSearchQuery.value = query
        viewModelScope.launch {
            applyFilter(getWordsUseCase.getWords(), query)
        }
    }

    fun onFavouriteClick(word: String) {
        viewModelScope.launch {
            val words = getWordsUseCase.getWords()
            val wordToUpdate = words.first { it.word == word }
            updateWordUseCase.updateWord(
                wordToUpdate.copy(isFavourite = !wordToUpdate.isFavourite)
            )
        }
    }

    fun addWord(word: String) {
        val newWord = Word(
            word = word,
            partOfSpeech = "",
            translate = "",
            isFavourite = false,
            definition = "",
            createdAt = System.currentTimeMillis(),
        )
        viewModelScope.launch {
            addWordUseCase.addWord(newWord)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            clearWordsUseCase.clearWords()
        }
        currentSearchQuery.value = ""
    }
}