package com.example.moviesapp.presentation.movie_details

import com.example.moviesapp.domain.model.MovieCredits
import com.example.moviesapp.domain.model.MovieDetails

data class MovieDetailsState(
    val movie: MovieDetails? = null,
    val isAddedToWatchList: Boolean = false,
    val movieCredits: MovieCredits? = null
)
