package com.minger.czechdictionary.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String,
) {
    object Add: BottomNavItem(route = "add", icon = Icons.Default.Add, title = "Add")
    object History: BottomNavItem(route = "history", icon = Icons.Default.History, title = "History")
    object Favourite: BottomNavItem(route = "favourite", icon = Icons.Default.Favorite, title = "Favourite")
}
