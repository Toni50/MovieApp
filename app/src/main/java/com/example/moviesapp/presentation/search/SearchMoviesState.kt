package com.example.moviesapp.presentation.search

import com.example.moviesapp.domain.model.Movie

data class SearchMoviesState(
    val searchText: String = "",
    val moviesList: List<Movie> = emptyList(),
    val isLoading: Boolean = false
)