package com.minger.czechdictionary.ui.word

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minger.czechdictionary.R
import com.minger.czechdictionary.presentation.word.WordState
import com.minger.czechdictionary.presentation.word.WordViewModel
import com.minger.czechdictionary.ui.common.AppBar
import com.minger.czechdictionary.ui.common.ErrorScreen
import com.minger.czechdictionary.ui.common.LoadingScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun WordScreen(
    modifier: Modifier = Modifier,
    viewModel: WordViewModel = koinViewModel(),
    word: String,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(word) { viewModel.loadWord(word) }
    when (val currentState = state) {
        WordState.Error -> ErrorScreen(modifier = modifier)
        WordState.Loading -> LoadingScreen(modifier = modifier)
        is WordState.Success -> SuccessContent(
            modifier = modifier,
            onBackClick = onBackClick,
            word = currentState.word,
            onFavouriteClick = viewModel::onFavouriteClick,
        )
    }
}

@Composable
private fun SuccessContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onFavouriteClick: () -> Unit,
    word: WordState.Success.Word,
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        AppBar(
            onBackClick = onBackClick,
            title = stringResource(R.string.word_screen_title),
            isBackClickNeed = true,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = word.word,
                fontSize = 32.sp,
            )
            Text(
                text = word.partOfSpeech,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                ItemButton(
                    icon = Icons.Default.ContentCopy,
                    buttonDescription = stringResource(R.string.copy_word),
                    action = {
                        val clipboard = context.getSystemService(ClipboardManager::class.java)
                        clipboard.setPrimaryClip(ClipData.newPlainText("word", word.word))
                        Toast.makeText(
                            context,
                            context.getString(R.string.word_copied),
                            Toast.LENGTH_SHORT,
                        ).show()
                    },
                )
                ItemButton(
                    icon = if (word.isFavourite) {
                        Icons.Default.FavoriteBorder
                    } else {
                        Icons.Default.Favorite
                    },
                    buttonDescription = if (word.isFavourite) {
                        stringResource(R.string.remove_from_favourite)
                    } else {
                        stringResource(R.string.add_to_favourite)
                    },
                    action = onFavouriteClick,
                )
                ItemButton(
                    icon = Icons.Default.Share,
                    buttonDescription = stringResource(R.string.share),
                    action = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, word.word)
                        }
                        context.startActivity(Intent.createChooser(shareIntent, null))
                    },
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            DescriptionCard(
                title = "Definition",
                description = word.definition
            )
            DescriptionCard(
                title = "Translation",
                description = word.translate
            )
        }
    }
}