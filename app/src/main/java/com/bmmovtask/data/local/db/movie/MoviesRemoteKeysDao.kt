package com.bmmovtask.data.local.db.movie

import androidx.room.*
import com.bmmovtask.data.model.movie.MovieEntityType
import com.bmmovtask.data.model.movie.MoviesRemoteKeys
import com.bmmovtask.utils.MovieEntityTypeConverters

@TypeConverters(MovieEntityTypeConverters::class)
@Dao
interface MoviesRemoteKeysDao {
    @Query("SELECT * FROM MoviesRemoteKeys WHERE type=:type AND language=:language")
    suspend fun getRemoteKey(type: MovieEntityType, language: String): MoviesRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: MoviesRemoteKeys)

    @Query("DELETE FROM MoviesRemoteKeys WHERE type=:type AND language=:language")
    suspend fun deleteRemoteKeysOfType(type: MovieEntityType, language: String)
}