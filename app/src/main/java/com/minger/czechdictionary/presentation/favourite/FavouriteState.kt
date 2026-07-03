package com.minger.czechdictionary.presentation.favourite

data class FavouriteWordItem(
    val word: String,
    val isFavourite: Boolean = true,
    val isRemoving: Boolean = false,
)

sealed class FavouriteState() {

    object Loading: FavouriteState()
    object Error: FavouriteState()
    data class Success(val words: List<FavouriteWordItem>): FavouriteState()
}
