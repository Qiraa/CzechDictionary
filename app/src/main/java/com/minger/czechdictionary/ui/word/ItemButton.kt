package com.minger.czechdictionary.ui.word

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ItemButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    buttonDescription: String,
    action: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier.padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = action,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = buttonDescription
        )
    }
}