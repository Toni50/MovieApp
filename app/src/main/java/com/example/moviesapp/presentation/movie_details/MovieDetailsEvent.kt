package com.example.moviesapp.presentation.movie_details

import com.example.moviesapp.domain.model.Movie


sealed class MovieDetailsEvent {
    data class AddMovieToWatchList(val movie: Movie?) : MovieDetailsEvent()
    data class RemoveMovieFromWatchList(val movie: Movie?) : MovieDetailsEvent()
}