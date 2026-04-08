package com.example.moviesapp.presentation.movie_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviesapp.data.mapper.toMovie
import com.example.moviesapp.presentation.components.CastItem
import com.example.moviesapp.presentation.components.Tag
import com.example.moviesapp.ui.theme.Sunflower
import java.time.LocalDate

@Composable
fun MovieDetailsScreen(
    movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = movieDetailsViewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()          // top safe area
            .navigationBarsPadding()      // bottom safe area
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val imageUrl = "https://image.tmdb.org/t/p/w780${state.value.movie?.backdropPath}"

            Box(modifier = Modifier.weight(0.3f)) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = state.value.movie?.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(10.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .weight(0.5f)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Play trailer",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Button(
                        {
                            movieDetailsViewModel.onEvent(
                                if (state.value.isAddedToWatchList) {
                                    MovieDetailsEvent.RemoveMovieFromWatchList(
                                        state.value.movie?.toMovie()
                                    )
                                } else {
                                    MovieDetailsEvent.AddMovieToWatchList(
                                        state.value.movie?.toMovie()
                                    )
                                }

                            )
                        }, Modifier
                            .padding(10.dp)
                            .weight(0.5f)
                            .border(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(10.dp)
                            ), colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector =
                                if (state.value.isAddedToWatchList)
                                    Icons.Filled.Remove
                                else Icons.Filled.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Watchlist",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(10.dp)
            ) {
                Text(
                    text = state.value.movie?.title ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = state.value.movie?.overview ?: "",
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column() {
                            Text(
                                "Release",
                                fontSize = 15.sp,
                                lineHeight = 16.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(text = state.value.movie?.releaseDate?.let {
                                LocalDate.parse(it).year.toString()
                            } ?: "",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 13.sp,
                                lineHeight = 14.sp)
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column() {
                            Text(
                                "Duration",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 15.sp,
                                lineHeight = 16.sp,
                            )
                            Text(
                                text = state.value.movie?.runtime?.let {
                                    val hours = it / 60
                                    val minutes = it % 60
                                    "${hours}h ${minutes}m"
                                } ?: "",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 13.sp,
                                lineHeight = 14.sp,
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Movie,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column() {
                            Text(
                                "Director",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 15.sp,
                                lineHeight = 16.sp
                            )
                            Text(
                                text = state.value.movieCredits?.crew?.firstOrNull { it.job == "Director" }
                                    .let {
                                        it?.name ?: ""
                                    },
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 13.sp,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row() {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Sunflower
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "%.1f".format(state.value.movie?.voteAverage ?: 0.0),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                state.value.movie?.genres?.let { genres ->
                    LazyRow {
                        items(genres) { genre ->
                            Tag(text = genre.name)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Cast",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                state.value.movieCredits?.cast?.let { castList ->
                    LazyRow {
                        items(castList) { movieCast ->
                            CastItem(movieCast)
                        }
                    }
                }
            }
        }
    }
}