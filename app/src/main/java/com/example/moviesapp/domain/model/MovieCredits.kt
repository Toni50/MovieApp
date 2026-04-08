package com.example.moviesapp.domain.model

data class MovieCredits(
    val id: Int?,
    val cast: List<Cast>?,
    val crew: List<Crew>?
)
