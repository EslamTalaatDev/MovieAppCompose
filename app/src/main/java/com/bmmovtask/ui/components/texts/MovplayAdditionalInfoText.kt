package com.bmmovtask.ui.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BmMoveAdditionalInfoText(
    infoTexts: List<String>,
    modifier: Modifier = Modifier
) {
    val text = infoTexts.joinToString(separator = " · ")

    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Normal
    )
}