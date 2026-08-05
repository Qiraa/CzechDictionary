package com.minger.czechdictionary.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.minger.czechdictionary.R
import com.minger.czechdictionary.presentation.history.HistoryState
import com.minger.czechdictionary.presentation.history.HistoryViewModel
import com.minger.czechdictionary.presentation.history.WordItem
import com.minger.czechdictionary.ui.common.AppBar
import com.minger.czechdictionary.ui.common.ErrorScreen
import com.minger.czechdictionary.ui.common.LoadingScreen
import com.minger.czechdictionary.ui.common.WordCard
import org.koin.androidx.compose.koinViewModel


@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel,
    onWordClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    when (val currentState = state) {
        HistoryState.Error -> ErrorScreen(modifier = modifier)
        HistoryState.Loading -> LoadingScreen(modifier = modifier)
        is HistoryState.Success -> SuccessContent(
            modifier = modifier,
            words = currentState.words,
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChanged,
            onFavouriteClick = viewModel::onFavouriteClick,
            onClearHistory = viewModel::clearHistory,
            onWordClick = onWordClick,
        )
    }
}

@Composable
private fun SuccessContent(
    modifier: Modifier = Modifier,
    words: List<WordItem>,
    query: String,
    onQueryChange: (String) -> Unit,
    onFavouriteClick: (String) -> Unit,
    onClearHistory: () -> Unit,
    onWordClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
        AppBar(
            title = stringResource(R.string.history_screen_title),
            isBackClickNeed = false,
            isRightIconNeed = true,
            onDeleteClick = onClearHistory,
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(stringResource(R.string.search)) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear_icon_content_description)
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(words, key = { it.word }) { item ->
                WordCard(
                    word = item.word,
                    isFavourite = item.isFavourite,
                    onWordClick = { onWordClick(item.word) },
                    updateWordClick = { onFavouriteClick(item.word) },
                )
            }
        }
    }
}