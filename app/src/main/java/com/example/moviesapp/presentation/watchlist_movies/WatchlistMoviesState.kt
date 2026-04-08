package com.example.moviesapp.presentation.watchlist_movies

import com.example.moviesapp.domain.model.Movie

data class WatchlistMoviesState(
    val moviesList: List<Movie> = emptyList(),
    val isLoading: Boolean = false
)
