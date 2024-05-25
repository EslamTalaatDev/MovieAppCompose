package com.bmmovtask.ui.screens.browse.movies

import android.os.Parcelable
import com.bmmovtask.data.model.movie.MovieType
import kotlinx.parcelize.Parcelize

@Parcelize
data class BrowseMoviesScreenArgs(
    val movieType: MovieType
) : Parcelable