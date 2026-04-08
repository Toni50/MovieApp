package com.example.moviesapp.presentation.watchlist_movies


sealed class WatchlistMovieEvent {
    data class OnClickMovie(val id: Int) : WatchlistMovieEvent()
}