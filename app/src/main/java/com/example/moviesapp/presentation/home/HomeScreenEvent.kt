package com.example.moviesapp.presentation.home

sealed class HomeScreenEvent {
    data class OnMovieClick(val id: Int) : HomeScreenEvent()
    object NavigateToSearchScreen : HomeScreenEvent()
}