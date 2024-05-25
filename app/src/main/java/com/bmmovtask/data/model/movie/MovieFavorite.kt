package com.bmmovtask.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bmmovtask.data.model.Presentable
import java.util.Date

@Entity
data class MovieFavorite(
    @PrimaryKey
    override val id: Int,
    @ColumnInfo(name = "poster_path")
    override val posterPath: String?,
    override val title: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "release_date")
    override val releaseDate: String,
    @ColumnInfo(name = "added_date")
    val addedDate: Date
) : Presentable
