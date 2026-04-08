package com.example.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesapp.data.local.movies.MovieEntity
import com.example.moviesapp.data.local.movies.MoviesDao
import com.example.moviesapp.data.local.watch_list.WatchlistMovieEntity
import com.example.moviesapp.data.local.watch_list.WatchlistMoviesDao

@Database(
    entities = [MovieEntity::class, WatchlistMovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    abstract fun watchlistMoviesDao(): WatchlistMoviesDao
}