package com.minger.czechdictionary.ui.favourite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.minger.czechdictionary.R
import com.minger.czechdictionary.presentation.favourite.FavouriteState
import com.minger.czechdictionary.presentation.favourite.FavouriteWordItem
import com.minger.czechdictionary.presentation.favourite.FavouriteViewModel
import com.minger.czechdictionary.ui.common.AppBar
import com.minger.czechdictionary.ui.common.ErrorScreen
import com.minger.czechdictionary.ui.common.LoadingScreen
import com.minger.czechdictionary.ui.common.WordCard
import org.koin.androidx.compose.koinViewModel


@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavouriteViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onWordClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    when (val currentSate = state) {
        FavouriteState.Error -> ErrorScreen(modifier = modifier)
        FavouriteState.Loading -> LoadingScreen(modifier = modifier)
        is FavouriteState.Success -> SuccessContent(
            modifier = modifier,
            words = currentSate.words,
            onWordClick = onWordClick,
            onFavouriteClick = viewModel::onWordClick,
            onBackClick = onBackClick,
        )
    }
}

@Composable
private fun SuccessContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onWordClick: (word: String) -> Unit,
    onFavouriteClick: (word: String) -> Unit,
    words: List<FavouriteWordItem>,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AppBar(
            onBackClick = onBackClick,
            title = stringResource(R.string.favourite_screen_title),
            isBackClickNeed = true,
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            items(words, key = { it.word }) { item ->
                AnimatedVisibility(
                    visible = !item.isRemoving,
                    exit = fadeOut() + slideOutVertically(),
                ) {
                    WordCard(
                        modifier = Modifier.animateItem(),
                        word = item.word,
                        isFavourite = item.isFavourite,
                        onWordClick = { onWordClick(item.word) },
                        updateWordClick = { onFavouriteClick(item.word) },
                    )
                }
            }
        }
    }
}
