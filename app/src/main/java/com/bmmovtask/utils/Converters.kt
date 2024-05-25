package com.bmmovtask.utils

import androidx.room.TypeConverter
import com.bmmovtask.data.model.movie.MovieEntityType
import java.util.Date

class DateConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

class MovieEntityTypeConverters {
    @TypeConverter
    fun toMovieEntityType(value: String) = enumValueOf<MovieEntityType>(value)

    @TypeConverter
    fun fromMovieEntityType(value: MovieEntityType) = value.name
}

