package com.example.moviesapp.data.repository

import com.example.moviesapp.data.local.MovieCategory
import com.example.moviesapp.data.local.MovieDatabase
import com.example.moviesapp.data.local.watch_list.WatchlistMovieEntity
import com.example.moviesapp.data.mapper.toMovie
import com.example.moviesapp.data.mapper.toMovieCredits
import com.example.moviesapp.data.mapper.toMovieDetails
import com.example.moviesapp.data.mapper.toMovieEntity
import com.example.moviesapp.data.mapper.toWatchlistMovieEntity
import com.example.moviesapp.data.remote.MoviesApi
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.model.MovieCredits
import com.example.moviesapp.domain.model.MovieDetails
import com.example.moviesapp.domain.repository.MovieRepository
import com.example.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(val moviesApi: MoviesApi, val db: MovieDatabase) :
    MovieRepository {
    private val moviesDao = db.moviesDao()

    private val watchListMoviesDao = db.watchlistMoviesDao()

    override suspend fun getTrendingMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val cachedMovies = moviesDao.getTrendingMoviesThisWeek()
            val isCacheValid = cachedMovies.isNotEmpty() &&
                    (System.currentTimeMillis() - cachedMovies.first().cachedAt) < 60 * 60 * 1000L // 1 hour

            if (!isCacheValid) {
                try {
                    moviesDao.clearTrendingMovies()

                    val response = moviesApi.getTrendingMoviesThisWeek(MoviesApi.API_KEY)

                    response?.let {
                        emit(
                            Resource.Success(
                                response.results.map { it.toMovie() }
                            )
                        )

                        moviesDao.insertTrendingMoviesThisWeek(response.results.map {
                            it.toMovieEntity(
                                MovieCategory.TRENDING,
                                System.currentTimeMillis()
                            )
                        })
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(data = null, message = "error"))
                }
            } else {
                emit(Resource.Success(data = cachedMovies.map { it.toMovie() }))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getPopularMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val cachedMovies = moviesDao.getPopularMoviesThisWeek()
            val isCacheValid = cachedMovies.isNotEmpty() &&
                    (System.currentTimeMillis() - cachedMovies.first().cachedAt) < 60 * 60 * 1000L // 1 hour

            if (!isCacheValid) {
                try {
                    val response = moviesApi.getPopularMoviesThisWeek(MoviesApi.API_KEY)

                    response?.let {
                        emit(
                            Resource.Success(
                                response.results.map { it.toMovie() }
                            )
                        )

                        moviesDao.insertPopularMoviesThisWeek(response.results.map {
                            it.toMovieEntity(
                                MovieCategory.POPULAR,
                                System.currentTimeMillis()
                            )
                        })
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(data = null, message = "error"))
                }
            } else {
                emit(Resource.Success(data = cachedMovies.map { it.toMovie() }))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTopRatedMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val cachedMovies = moviesDao.getTopRatedMovies()
            val isCacheValid = cachedMovies.isNotEmpty() &&
                    (System.currentTimeMillis() - cachedMovies.first().cachedAt) < 60 * 60 * 1000L // 1 hour

            if (!isCacheValid) {
                try {
                    val response = moviesApi.getTopRatedMoviesThisWeek(MoviesApi.API_KEY)

                    response?.let {
                        emit(
                            Resource.Success(
                                response.results.map { it.toMovie() }
                            )
                        )

                        moviesDao.insertPopularMoviesThisWeek(response.results.map {
                            it.toMovieEntity(
                                MovieCategory.TOP_RATED,
                                System.currentTimeMillis()
                            )
                        })
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(data = null, message = "error"))
                }
            } else {
                emit(Resource.Success(data = cachedMovies.map { it.toMovie() }))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>> {
        return flow {

            try {
                val response = moviesApi.getMovie(id, MoviesApi.API_KEY)

                response?.let {
                    emit(
                        Resource.Success(
                            response.toMovieDetails()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(Resource.Error(data = null, message = "error"))
            }
        }
    }

    override suspend fun searchMovie(searchText: String): Flow<Resource<List<Movie>>> {
        return flow {
            try {
                val response = moviesApi.searchMovies(MoviesApi.API_KEY, searchText)

                response?.let {
                    emit(
                        Resource.Success(
                            response.results.map { it.toMovie() }
                        )
                    )
                }
            } catch (e: Exception) {
                emit(Resource.Error(data = null, message = "error"))
            }
        }
    }

    override suspend fun getMovieCredits(id: Int): Flow<Resource<MovieCredits>> {
        return flow {
            try {
                val response = moviesApi.getMovieCredits(id, MoviesApi.API_KEY)

                response?.let {
                    emit(
                        Resource.Success(
                            response.toMovieCredits()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(Resource.Error(data = null, message = "error"))
            }
        }
    }

    override suspend fun addMovieToWatchList(movie: Movie?): Flow<Resource<Movie>> {
        return flow {
            try {
                if (movie == null) {
                    emit(Resource.Error(data = null, message = "error"))
                    return@flow
                }

                watchListMoviesDao.insertWatchlistMovie(movie.toWatchlistMovieEntity())

                emit(
                    Resource.Success(
                        movie
                    )
                )

            } catch (e: Exception) {
                emit(Resource.Error(data = null, message = "error"))
            }
        }
    }

    override fun getWatchlistMoviesFlow(): Flow<Resource<List<Movie>>> {
        return watchListMoviesDao.getWatchlistMoviesFlow()
            .map<List<WatchlistMovieEntity>, Resource<List<Movie>>> { movies ->
                Resource.Success(movies.map { it.toMovie() })
            }
            .onStart { emit(Resource.Loading<List<Movie>>()) }
            .catch { e ->
                emit(Resource.Error<List<Movie>>(message = e.message ?: "Unknown error"))
            }
    }

    override suspend fun checkIfIsAddedToWatchList(id: Int): Flow<Resource<Boolean>> {
        return flow {
            try {

                val exists = watchListMoviesDao.isMovieInWatchlist(id)

                emit(
                    Resource.Success(
                        exists
                    )
                )

            } catch (e: Exception) {
                emit(Resource.Error(data = null, message = "error"))
            }
        }
    }

    override suspend fun removeFromWatchlistMovies(id: Int): Flow<Resource<Boolean>> {
        return flow {
            try {
                watchListMoviesDao.removeFromWatchlist(id)

                emit(
                    Resource.Success(
                        true
                    )
                )

            } catch (e: Exception) {
                emit(Resource.Error(data = null, message = "error"))
            }
        }
    }
}