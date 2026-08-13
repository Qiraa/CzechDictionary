package com.minger.czechdictionary.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.minger.czechdictionary.presentation.history.HistoryViewModel
import com.minger.czechdictionary.ui.add.AddScreen
import com.minger.czechdictionary.ui.favourite.FavouriteScreen
import com.minger.czechdictionary.ui.history.HistoryScreen
import com.minger.czechdictionary.ui.word.WordScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val historyViewModel: HistoryViewModel = koinViewModel()
    var showAddSheet by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                navController = navController,
                onAddClick = { showAddSheet = true },
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.History.route,
            modifier = modifier.padding(padding)
        ) {
            composable(BottomNavItem.History.route) {
                HistoryScreen(
                    viewModel = historyViewModel,
                    onWordClick = { word -> navController.navigate("word/$word") }
                )
            }

            composable(BottomNavItem.Favourite.route) {
                FavouriteScreen(
                    onBackClick = { navController.popBackStack() },
                    onWordClick = { word -> navController.navigate("word/$word") }
                )
            }

            composable(
                route = "word/{word}",
                arguments = listOf(navArgument("word") { type = NavType.StringType})
            ) {
                backStackEntry ->
                val word = backStackEntry.arguments?.getString("word").orEmpty()
                WordScreen(
                    word = word,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }

    if (showAddSheet) {
        AddScreen(
            onAddWord = { word ->
                historyViewModel.addWord(word)
                showAddSheet = false
            },
            onDismiss = { showAddSheet = false },
        )
    }
}