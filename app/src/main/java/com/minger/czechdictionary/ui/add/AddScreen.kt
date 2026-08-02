package com.minger.czechdictionary.ui.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.minger.czechdictionary.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(onAddWord: (String) -> Unit, onDismiss: () -> Unit) {
    var text by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                label = { Text(stringResource(R.string.add_word)) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onAddWord(text)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.add))
            }
        }
    }
}