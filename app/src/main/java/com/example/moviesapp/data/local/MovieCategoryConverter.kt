package com.example.moviesapp.data.local

import androidx.room.TypeConverter

class MovieCategoryConverter {
    @TypeConverter
    fun fromCategory(category: MovieCategory): String = category.name

    @TypeConverter
    fun toCategory(value: String): MovieCategory = MovieCategory.valueOf(value)
}