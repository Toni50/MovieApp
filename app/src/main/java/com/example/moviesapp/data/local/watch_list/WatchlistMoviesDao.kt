package com.example.moviesapp.data.local.watch_list

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistMoviesDao {

    @Query("SELECT * FROM watchlistmovieentity")
    fun getWatchlistMoviesFlow(): Flow<List<WatchlistMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchlistMovie(watchlistMovieEntity: WatchlistMovieEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM watchlistmovieentity WHERE id = :movieId)")
    suspend fun isMovieInWatchlist(movieId: Int): Boolean

    @Query("DELETE FROM watchlistmovieentity WHERE id = :movieId")
    suspend fun removeFromWatchlist(movieId: Int)
}