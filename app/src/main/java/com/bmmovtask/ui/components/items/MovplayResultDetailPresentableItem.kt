package com.bmmovtask.ui.components.items

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.size.Scale
import com.bmmovtask.data.model.DetailPresentable
import com.bmmovtask.ui.components.chips.BmMoveAdultChips
import com.bmmovtask.ui.theme.spacing
import com.bmmovtask.utils.ImageUrlParser
import com.bmmovtask.utils.TmdbImage

@SuppressLint("UnrememberedMutableState")
@Composable
fun BmMoveResultDetailPresentableItem(
    presentable: DetailPresentable,
    modifier: Modifier = Modifier,
    showTitle: Boolean = true,
    showAdult: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val hasPoster by derivedStateOf {
        presentable.posterPath != null
    }

    Box(modifier = modifier
        .clickable(
            enabled = onClick != null,
            onClick = { onClick?.invoke() }
        )
    ) {
        if (hasPoster) {
            TmdbImage(
                modifier = Modifier.matchParentSize(),
                imagePath = presentable.posterPath,
                imageType = ImageUrlParser.ImageType.Poster
            ) {
                size(coil.size.Size.ORIGINAL)
                scale(Scale.FILL)
                crossfade(true)
            }
        } else {
            BmMoveNoPhotoPresentableItem(
                modifier = Modifier.fillMaxSize()
            )
        }

        if (showTitle) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(MaterialTheme.spacing.extraSmall),
                    text = presentable.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
        }


        if (presentable.adult == true && showAdult) {
            BmMoveAdultChips(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = MaterialTheme.spacing.extraSmall,
                        bottom = MaterialTheme.spacing.extraSmall
                    )
            )
        }
    }
}