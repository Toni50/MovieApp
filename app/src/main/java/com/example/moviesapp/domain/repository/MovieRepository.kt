package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.model.MovieCredits
import com.example.moviesapp.domain.model.MovieDetails
import com.example.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getTrendingMovies(): Flow<Resource<List<Movie>>>

    suspend fun getPopularMovies(): Flow<Resource<List<Movie>>>

    suspend fun getTopRatedMovies(): Flow<Resource<List<Movie>>>

    suspend fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>>

    suspend fun getMovieCredits(id: Int): Flow<Resource<MovieCredits>>

    fun getWatchlistMoviesFlow(): Flow<Resource<List<Movie>>>

    suspend fun addMovieToWatchList(movie: Movie?): Flow<Resource<Movie>>

    suspend fun removeFromWatchlistMovies(id: Int): Flow<Resource<Boolean>>

    suspend fun checkIfIsAddedToWatchList(id: Int): Flow<Resource<Boolean>>

    suspend fun searchMovie(searchText: String): Flow<Resource<List<Movie>>>
}