package com.minger.czechdictionary.presentation.word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minger.czechdictionary.data.Word
import com.minger.czechdictionary.domain.GetWordUseCase
import com.minger.czechdictionary.domain.UpdateWordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WordViewModel(
    private val getWordUseCase: GetWordUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
) : ViewModel() {

    private val mutableState: MutableStateFlow<WordState> = MutableStateFlow(WordState.Loading)

    val state: StateFlow<WordState> = mutableState.asStateFlow()

    fun loadWord(word: String) {
        viewModelScope.launch {
            getWordUseCase.getWord(word)
        }
        viewModelScope.launch {
            getWordUseCase.observeWord(word).collect { gotWord ->
                mutableState.value = WordState.Success(
                    word = WordState.Success.Word(
                        word = gotWord.word,
                        partOfSpeech = gotWord.partOfSpeech,
                        translate = gotWord.translate,
                        isFavourite = gotWord.isFavourite,
                        definition = gotWord.definition,
                        createdAt = gotWord.createdAt,
                    )
                )
            }
        }
    }

    fun onFavouriteClick() {
        val current = mutableState.value
        if (current is WordState.Success) {
            val updated = current.word.copy(isFavourite = !current.word.isFavourite)
            viewModelScope.launch {
                updateWordUseCase.updateWord(
                    Word(
                        word = updated.word,
                        partOfSpeech = updated.partOfSpeech,
                        translate = updated.translate,
                        isFavourite = updated.isFavourite,
                        definition = updated.definition,
                        createdAt = updated.createdAt,
                    )
                )
            }
        }
    }
}