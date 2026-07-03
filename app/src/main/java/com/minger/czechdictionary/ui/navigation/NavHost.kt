package com.minger.czechdictionary.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minger.czechdictionary.ui.add.AddScreen
import com.minger.czechdictionary.ui.favourite.FavouriteScreen
import com.minger.czechdictionary.ui.history.HistoryScreen

@Composable
fun NavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            CustomBottomBar(navController = navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Favourite.route,
            modifier = modifier.padding(padding)
        ) {
            composable(BottomNavItem.Add.route) {
                AddScreen()
            }

            composable(BottomNavItem.History.route) {
                HistoryScreen()
            }

            composable(BottomNavItem.Favourite.route) {
                FavouriteScreen(onBackClick = { navController.popBackStack() })
            }
        }
    }
}