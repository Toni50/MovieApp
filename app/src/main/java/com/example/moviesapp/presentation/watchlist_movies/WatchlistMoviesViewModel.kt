package com.example.moviesapp.presentation.watchlist_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WatchlistMoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WatchlistMoviesState())
    val state: StateFlow<WatchlistMoviesState> = _state.asStateFlow()


    private val _navigationEvent = MutableSharedFlow<WatchlistMovieEvent>() // movieId
    val navigationEvent = _navigationEvent.asSharedFlow()


    init {
        observeWatchListMovies()
    }

    fun onEvent(event: WatchlistMovieEvent) {
        when (event) {
            is WatchlistMovieEvent.OnClickMovie -> {
                viewModelScope.launch {
                    _navigationEvent.emit(event)
                }
            }
        }
    }

    fun observeWatchListMovies() {
        // Collect Flow reactively
        viewModelScope.launch {
            repository.getWatchlistMoviesFlow().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let {
                            _state.value = _state.value.copy(
                                moviesList = result.data ?:  emptyList()
                            )
                        }
                    }

                    is Resource.Loading -> Unit
                    is Resource.Error -> Unit
                }
            }
        }
    }
}