package com.example.moviesapp.data.mapper

import com.example.moviesapp.data.local.MovieCategory
import com.example.moviesapp.data.local.movies.MovieEntity
import com.example.moviesapp.data.local.watch_list.WatchlistMovieEntity
import com.example.moviesapp.data.remote.dto.MovieCreditsDto
import com.example.moviesapp.data.remote.dto.MovieDetailsDto
import com.example.moviesapp.data.remote.dto.MovieDto
import com.example.moviesapp.domain.model.Cast
import com.example.moviesapp.domain.model.CollectionInfo
import com.example.moviesapp.domain.model.Crew
import com.example.moviesapp.domain.model.Genre
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.model.MovieCredits
import com.example.moviesapp.domain.model.MovieDetails
import com.example.moviesapp.domain.model.ProductionCompany
import com.example.moviesapp.domain.model.ProductionCountry
import com.example.moviesapp.domain.model.SpokenLanguage

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}


fun MovieDto.toMovieEntity(movieCategory: MovieCategory, cachedAt: Long): MovieEntity {
    return MovieEntity(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        movieCategory = movieCategory,
        cachedAt = cachedAt
    )
}


fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        overview = overview,
        tagline = tagline,
        adult = adult,
        video = video,
        backdropPath = backdropPath,
        posterPath = posterPath,
        belongsToCollection = belongsToCollection?.let { collection ->
            CollectionInfo(
                id = collection.id,
                name = collection.name,
                posterPath = collection.posterPath,
                backdropPath = collection.backdropPath
            )
        },
        genres = genres.map { Genre(it.id, it.name) },
        homepage = homepage,
        releaseDate = releaseDate,
        runtime = runtime,
        budget = budget,
        revenue = revenue,
        popularity = popularity,
        voteAverage = voteAverage,
        voteCount = voteCount,
        imdbId = imdbId,
        originCountry = originCountry,
        productionCompanies = productionCompanies?.map {
            ProductionCompany(it.id, it.logoPath, it.name, it.originCountry)
        },
        productionCountries = productionCountries?.map {
            ProductionCountry(it.iso31661, it.name)
        },
        spokenLanguages = spokenLanguages?.map {
            SpokenLanguage(it.englishName, it.iso6391, it.name)
        }
    )
}


fun MovieCreditsDto.toMovieCredits(): MovieCredits {
    return MovieCredits(
        id = id,
        cast = cast?.map {
            Cast(
                it?.adult,
                it?.castId,
                it?.character,
                it?.creditId,
                it?.gender,
                it?.id,
                it?.knownForDepartment,
                it?.name,
                it?.order,
                it?.originalName,
                it?.popularity,
                it?.profilePath
            )
        },
        crew = crew?.map {
            Crew(
                it?.adult,
                it?.creditId,
                it?.department,
                it?.gender,
                it?.id,
                it?.job,
                it?.knownForDepartment,
                it?.name,
                it?.originalName,
                it?.popularity,
                it?.profilePath
            )
        }
    )
}


fun MovieDetails.toMovie(): Movie {
    return Movie(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genres.map { it.id },
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

fun Movie.toWatchlistMovieEntity(): WatchlistMovieEntity {
    return WatchlistMovieEntity(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
    )
}


fun WatchlistMovieEntity.toMovie():Movie{
    return Movie(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
    )
}