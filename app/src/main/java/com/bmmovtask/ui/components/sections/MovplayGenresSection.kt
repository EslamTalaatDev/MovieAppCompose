package com.bmmovtask.ui.components.sections

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bmmovtask.data.model.Genre
import com.bmmovtask.ui.components.chips.BmMoveGenreChip
import com.bmmovtask.ui.theme.spacing
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun BmMoveGenresSection(
    genres: List<Genre>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        mainAxisSpacing = MaterialTheme.spacing.extraSmall,
        crossAxisSpacing = MaterialTheme.spacing.extraSmall
    ) {
        genres.map { genre ->
            BmMoveGenreChip(text = genre.name)
        }
    }
}