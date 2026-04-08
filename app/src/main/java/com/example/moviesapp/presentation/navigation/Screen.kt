package com.example.moviesapp.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object MovieDetails: Screen(route = "movie-details/{id}"){
        fun createRoute(id: Int) = "movie-details/$id"
    }
    object Search : Screen("search")

    object Watchlist : Screen("watchlist")
}