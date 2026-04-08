package com.example.moviesapp.data.local.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movieentity WHERE movieCategory = 'TRENDING'")
    suspend fun getTrendingMoviesThisWeek():List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertTrendingMoviesThisWeek(movieEntity: List<MovieEntity>)

    @Query("DELETE FROM movieentity WHERE movieCategory = 'TRENDING'")
    suspend fun clearTrendingMovies()

    @Query("SELECT * FROM movieentity WHERE movieCategory = 'POPULAR'")
    suspend fun getPopularMoviesThisWeek():List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertPopularMoviesThisWeek(movieEntity: List<MovieEntity>)

    @Query("DELETE FROM movieentity WHERE movieCategory = 'POPULAR'")
    suspend fun clearPopularMovies()

    @Query("SELECT * FROM movieentity WHERE movieCategory = 'TOP_RATED'")
    suspend fun getTopRatedMovies():List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertTopRatedMoviesThisWeek(movieEntity: List<MovieEntity>)

    @Query("DELETE FROM movieentity WHERE movieCategory = 'TOP_RATED'")
    suspend fun clearTopRatedMovies()
}