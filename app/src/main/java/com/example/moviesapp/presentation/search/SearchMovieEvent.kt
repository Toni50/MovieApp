package com.example.moviesapp.presentation.search



sealed class SearchMovieEvent {
    data class OnSearchMovie(val text: String) : SearchMovieEvent()
    data class OnClickMovie(val id: Int) : SearchMovieEvent()
}