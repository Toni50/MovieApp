package com.example.moviesapp.presentation.home

import com.example.moviesapp.domain.model.Movie

data class HomeState(
    val trendingMoviesList: List<Movie> = emptyList(),
    val popularMoviesList: List<Movie> = emptyList(),
    val topRatedMoviesList: List<Movie> = emptyList()
)