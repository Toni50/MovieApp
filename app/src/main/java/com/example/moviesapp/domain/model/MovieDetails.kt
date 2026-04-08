package com.example.moviesapp.domain.model


data class MovieDetails(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val overview: String,
    val tagline: String,
    val adult: Boolean,
    val video: Boolean,
    val backdropPath: String,
    val posterPath: String,
    val belongsToCollection: CollectionInfo?,
    val genres: List<Genre>,
    val homepage: String,
    val releaseDate: String,
    val runtime: Int,
    val budget: Int,
    val revenue: Int,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val imdbId: String,
    val originCountry: List<String>,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>?,
    val spokenLanguages: List<SpokenLanguage>?
)