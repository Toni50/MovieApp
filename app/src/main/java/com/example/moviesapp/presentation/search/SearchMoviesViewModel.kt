package com.example.moviesapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchMoviesState())
    val state: StateFlow<SearchMoviesState> = _state.asStateFlow()


    private val _navigationEvent = MutableSharedFlow<Int>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        observeSearch()
    }


    fun onEvent(event: SearchMovieEvent) {
        when (event) {
            is SearchMovieEvent.OnSearchMovie -> {
                _state.value = _state.value.copy(
                    searchText = event.text
                )
                searchQuery.value = event.text    // updates the flow
            }

            is SearchMovieEvent.OnClickMovie -> {
                viewModelScope.launch {
                    _navigationEvent.emit(event.id)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun observeSearch() {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .map { it.trim() } //trim the query to avoid sending spaces to API:
                .distinctUntilChanged() //skips repeated queries
                .onEach { query -> //clears UI when text is empty
                    if (query.isBlank()) {
                        _state.value = _state.value.copy(
                            moviesList = emptyList(),
                            isLoading = false
                        )
                    }
                }
                .filter { it.isNotBlank() } //avoids unnecessary API calls
                .flatMapLatest { query ->   // cancels previous search automatically
                    repository.searchMovie(query)
                }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data.let {
                                _state.value = _state.value.copy(
                                    moviesList = result.data ?: emptyList(),
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = result.isLoading)
                        }

                        is Resource.Error -> Unit
                    }
                }
        }
    }
}