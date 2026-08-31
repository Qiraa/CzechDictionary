package com.minger.czechdictionary.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.minger.czechdictionary.R

@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    onAddClick: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            containerColor = Color.White
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomBarItem(
                    item = BottomNavItem.History,
                    onClick = { navController.navigate(BottomNavItem.History.route) },
                    contentDescription = stringResource(R.string.history)
                )
                Spacer(modifier = Modifier.width(72.dp))
                BottomBarItem(
                    item = BottomNavItem.Favourite,
                    onClick = { navController.navigate(BottomNavItem.Favourite.route) },
                    contentDescription = stringResource(R.string.favourite)
                )
            }
        }
        FloatingActionButton(
            onClick = onAddClick,
            modifier = Modifier
                .offset(y = (-20).dp)
                .size(72.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add),
                tint = Color.White,
            )
        }
    }
}
