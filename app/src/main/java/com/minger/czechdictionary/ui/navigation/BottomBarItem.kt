package com.minger.czechdictionary.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomBarItem(
    modifier: Modifier = Modifier,
    item: BottomNavItem,
    onClick: () -> Unit,
    contentDescription: String,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(28.dp),
        )
    }
}