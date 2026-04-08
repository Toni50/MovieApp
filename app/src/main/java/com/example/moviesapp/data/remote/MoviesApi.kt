package com.example.moviesapp.data.remote

import com.example.moviesapp.data.remote.dto.MovieCreditsDto
import com.example.moviesapp.data.remote.dto.MovieDetailsDto
import com.example.moviesapp.data.remote.dto.MovieDto
import com.example.moviesapp.data.remote.dto.ResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("trending/movie/week")
    suspend fun getTrendingMoviesThisWeek(@Query("api_key") apiKey: String): ResultDto<MovieDto>?

    @GET("movie/popular")
    suspend fun getPopularMoviesThisWeek(@Query("api_key") apiKey: String): ResultDto<MovieDto>?

    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesThisWeek(@Query("api_key") apiKey: String): ResultDto<MovieDto>?

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Int, @Query("api_key") apiKey: String): MovieDetailsDto

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): MovieCreditsDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): ResultDto<MovieDto>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "4fd03414588bd154071a6a884d959c78"
    }
}