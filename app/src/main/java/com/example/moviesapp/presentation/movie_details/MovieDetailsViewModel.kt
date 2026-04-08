package com.example.moviesapp.presentation.movie_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState> = _state


    private val id: Int = savedStateHandle["id"] ?: 0

    init {
        getMovieDetails()
        getMovieCredits()
        checkIfIsAddedToWatchList()
    }

    fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.AddMovieToWatchList -> {
                viewModelScope.launch {
                    movieRepository.addMovieToWatchList(event.movie).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    isAddedToWatchList = true
                                )
                            }

                            is Resource.Loading -> Unit
                            is Resource.Error -> Unit
                        }
                    }
                }
            }

            is MovieDetailsEvent.RemoveMovieFromWatchList -> {
                viewModelScope.launch {
                    event.movie?.id?.let {
                        movieRepository.removeFromWatchlistMovies(event.movie.id)
                            .collect { result ->
                                when (result) {
                                    is Resource.Success -> {
                                        _state.value = _state.value.copy(
                                            isAddedToWatchList = false)
                                    }

                                    is Resource.Loading -> Unit
                                    is Resource.Error -> Unit
                                }
                            }
                    }
                }
            }
        }
    }

    fun getMovieDetails() {
        viewModelScope.launch {
            movieRepository.getMovieDetails(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let {
                            _state.value = _state.value.copy(
                                movie = result.data
                            )
                        }
                    }

                    is Resource.Loading -> Unit
                    is Resource.Error -> Unit
                }
            }
        }
    }

    fun getMovieCredits() {
        viewModelScope.launch {
            movieRepository.getMovieCredits(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let {
                            _state.value = _state.value.copy(
                                movieCredits = result.data
                            )
                        }
                    }

                    is Resource.Loading -> Unit
                    is Resource.Error -> Unit
                }
            }
        }
    }

    fun checkIfIsAddedToWatchList() {
        viewModelScope.launch {
            movieRepository.checkIfIsAddedToWatchList(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let {
                            _state.value = _state.value.copy(
                                isAddedToWatchList = result.data ?: false
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