package com.bmmovtask.ui.components.others

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bmmovtask.utils.defaultPlaceholder

@Composable
fun PosterPlaceholder(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.defaultPlaceholder()
    )
}
