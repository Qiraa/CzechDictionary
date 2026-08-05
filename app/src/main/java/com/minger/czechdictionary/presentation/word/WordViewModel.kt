package com.minger.czechdictionary.presentation.word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minger.czechdictionary.data.Word
import com.minger.czechdictionary.domain.UpdateWordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WordViewModel(
    private val UpdateWordUseCase: UpdateWordUseCase,
) : ViewModel() {

    private val mutableState: MutableStateFlow<WordState> = MutableStateFlow(WordState.Loading)

    val state: StateFlow<WordState> = mutableState.asStateFlow()

    fun loadWord(word: String) {
        mutableState.value = WordState.Success(
            word = WordState.Success.Word(
                word = word,
                partOfSpeech = "noun",
                translate = "",
                isFavourite = true,
                definition = ""
            )
        )
    }

    fun onFavouriteClick() {
        val current = mutableState.value
        if (current is WordState.Success) {
            val updated = current.word.copy(isFavourite = !current.word.isFavourite)
            mutableState.value = WordState.Success(updated)
            viewModelScope.launch {
                UpdateWordUseCase.updateWord(
                    Word(
                        word = updated.word,
                        partOfSpeech = updated.partOfSpeech,
                        translate = updated.translate,
                        isFavourite = updated.isFavourite,
                        definition = updated.definition,
                    )
                )
            }
        }
    }
}