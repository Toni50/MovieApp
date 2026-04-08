package com.example.moviesapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        getTrendingMovies()
        getPopularMovies()
        getTopRatedMovies()
    }

    private val _navigationEvent = MutableSharedFlow<HomeScreenEvent>() // movieId
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnMovieClick -> {
                // emit the ID, UI will handle navigation
                viewModelScope.launch {
                    _navigationEvent.emit(event)
                }
            }
            is HomeScreenEvent.NavigateToSearchScreen ->{
                viewModelScope.launch {
                    _navigationEvent.emit(event)
                }
            }
        }
    }

    fun getTrendingMovies() {
        viewModelScope.launch {
            repository.getTrendingMovies().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let {
                            _state.value = _state.value.copy(
                                trendingMoviesList = result.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Loading -> Unit

                    is Resource.Error -> Unit
                }
            }

        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            repository.getPopularMovies().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let {
                            _state.value = _state.value.copy(
                                popularMoviesList = result.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Loading -> Unit

                    is Resource.Error -> Unit
                }
            }

        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            repository.getTopRatedMovies().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let {
                            _state.value = _state.value.copy(
                                topRatedMoviesList = result.data ?: emptyList()
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