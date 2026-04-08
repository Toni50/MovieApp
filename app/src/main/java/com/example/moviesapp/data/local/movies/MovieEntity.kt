package com.example.moviesapp.data.local.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviesapp.data.local.MovieCategory
import com.example.moviesapp.data.local.MovieCategoryConverter

@Entity
@TypeConverters(MovieCategoryConverter::class)
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean?,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val movieCategory: MovieCategory,
    val cachedAt: Long
)