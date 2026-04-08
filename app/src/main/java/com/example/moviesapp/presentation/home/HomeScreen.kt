package com.example.moviesapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviesapp.presentation.components.Carousel
import com.example.moviesapp.presentation.components.MovieCategorySection
import com.example.moviesapp.presentation.components.SearchBar
import com.example.moviesapp.presentation.navigation.Screen

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navController: NavController) {

    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when(event){
                is HomeScreenEvent.OnMovieClick ->{
                    navController.navigate(Screen.MovieDetails.createRoute(event.id))
                }
                is HomeScreenEvent.NavigateToSearchScreen ->{
                    navController.navigate(Screen.Search.route)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()          // top safe area
            .navigationBarsPadding()      // bottom safe area
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {

            SearchBar(onClick = {
                viewModel.onEvent(HomeScreenEvent.NavigateToSearchScreen)
            })

            MovieCategorySection("Trending")
            Carousel(
                movies = state.value.trendingMoviesList.take(10),
                onMovieClick = { movie ->
                    viewModel.onEvent(HomeScreenEvent.OnMovieClick(movie.id))
                },
                itemHeight = 300.dp,
                animationRotationDelay = 2000
            )

            MovieCategorySection("Popular")
            Carousel(
                movies = state.value.popularMoviesList,
                onMovieClick = { movie ->
                    viewModel.onEvent(HomeScreenEvent.OnMovieClick(movie.id))
                },
                itemHeight = 200.dp,

                animationRotationDelay = 1500,
                itemsPerPage = 3,
                showPagerIndicator = false
            )

            MovieCategorySection("Top Rated")
            Carousel(
                movies = state.value.topRatedMoviesList,
                onMovieClick = { movie ->
                    viewModel.onEvent(HomeScreenEvent.OnMovieClick(movie.id))
                },
                itemHeight = 200.dp,
                animationRotationDelay = 1000,
                itemsPerPage = 3,
                showPagerIndicator = false
            )
        }
    }
}