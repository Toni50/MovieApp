package com.example.moviesapp.presentation.watchlist_movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviesapp.presentation.components.MovieListItem
import com.example.moviesapp.presentation.navigation.Screen


@Composable
fun WatchlistMoviesScreen(
    viewModel: WatchlistMoviesViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when(event){
                is WatchlistMovieEvent.OnClickMovie ->{
                    navController.navigate(Screen.MovieDetails.createRoute(event.id))
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .statusBarsPadding()          // top safe area
            .navigationBarsPadding()      // bottom safe area
    ) {

        LazyColumn() {
            items(state.value.moviesList){ movie ->
                Box(modifier = Modifier.padding(10.dp)) {
                    MovieListItem(movie, onClick = {
                        viewModel.onEvent(WatchlistMovieEvent.OnClickMovie(movie.id))
                    })
                }
            }
        }
    }
}