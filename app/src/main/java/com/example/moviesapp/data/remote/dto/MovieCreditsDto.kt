package com.example.moviesapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MovieCreditsDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val cast: List<CastDto?>?,
    @SerializedName("crew")
    val crew: List<CrewDto?>?
)