package com.minger.czechdictionary.presentation.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minger.czechdictionary.domain.GetWordUseCase
import com.minger.czechdictionary.domain.GetWordsUseCase
import com.minger.czechdictionary.domain.UpdateWordUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class FavouriteViewModel(
    private val updateWordUseCase: UpdateWordUseCase,
    private val getWordUseCase: GetWordUseCase,
    private val getWordsUseCase: GetWordsUseCase,
    ): ViewModel() {

    private val mutableState: MutableStateFlow<FavouriteState> = MutableStateFlow(FavouriteState.Loading)
    val state: StateFlow<FavouriteState> = mutableState.asStateFlow()

    init {
        loadWords()
    }

    private fun loadWords() {
        viewModelScope.launch {
            mutableState.value = FavouriteState.Loading
            val favouriteWords = getWordsUseCase.getWords().filter {it.isFavourite}
            mutableState.value = FavouriteState.Success(
                favouriteWords.map {
                    FavouriteWordItem(
                        word = it.word,
                        isFavourite = it.isFavourite
                    )
                }
            )
        }
    }
    fun onWordClick(word: String) {
        val current = mutableState.value
        if (current is FavouriteState.Success) {
            val updatedWords = current.words.map {
                if (it.word == word) it.copy(isFavourite = false, isRemoving = true) else it
            }
            mutableState.value = FavouriteState.Success(updatedWords)

            viewModelScope.launch {
                val wordToUpdate = getWordsUseCase.getWords().first { it.word == word }
                updateWordUseCase.updateWord(
                    wordToUpdate.copy(isFavourite = false)
                )
                mutableState.value = FavouriteState.Success(
                    updatedWords.filter { it.word != word }
                )
            }
        }
    }
}
