package com.example.moviesapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.presentation.MovieCardSizeType
import com.example.moviesapp.ui.theme.Sunflower
import java.time.LocalDate

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClick: (Movie) -> Unit = {},
    movieCardSizeType: MovieCardSizeType = MovieCardSizeType.LARGE
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(15.dp)),
        onClick = { onClick(movie) }) {
        Box(
            contentAlignment = Alignment.BottomStart
        ) {
            val imageUrl = "https://image.tmdb.org/t/p/w780${movie.backdropPath}" //landscape

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            Column(
                modifier = Modifier
                    .background(Color.Black.copy(0.2f))
                    .padding(
                        10.dp,
                        10.dp,
                        10.dp,
                        if (movieCardSizeType == MovieCardSizeType.LARGE) 30.dp
                        else 10.dp
                    )
            ) {
                Text(
                    text = movie.title ?: "",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = if (movieCardSizeType == MovieCardSizeType.LARGE) 20.sp else 15.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (movieCardSizeType == MovieCardSizeType.LARGE) {
                    Text(
                        text = movie.overview ?: "",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row() {
                    Text(
                        text = LocalDate.parse(movie.releaseDate).year.toString(),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 15.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Sunflower
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "%.1f".format(movie.voteAverage),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 15.sp,
                    )
                }
            }
        }
    }
}