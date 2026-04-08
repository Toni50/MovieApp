package com.example.moviesapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.presentation.MovieCardSizeType
import kotlinx.coroutines.delay

@Composable
fun Carousel(
    movies: List<Movie>, onMovieClick: (Movie) -> Unit, itemHeight: Dp = 200.dp,
    animationRotationDelay: Long = 2000, itemsPerPage: Int = 1, showPagerIndicator: Boolean = true
) {
    val pageCount = if (itemsPerPage == 1) {
        movies.size
    } else {
        (movies.size + itemsPerPage - 1) / itemsPerPage
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageCount }
    )

    LaunchedEffect(movies) {
        if (movies.isEmpty())
            return@LaunchedEffect

        while (true) {
            delay(animationRotationDelay)
            val nextPage = (pagerState.currentPage + 1) % pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column() {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 30.dp),
            pageSpacing = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
        ) { page ->
            Box() {
                if (itemsPerPage == 1) {
                    val movie = movies[page]
                    MovieCard(
                        movie = movie,
                        modifier = Modifier.fillMaxSize(),
                        onClick = { onMovieClick(movie) })
                } else {
                    val startIndex = page * itemsPerPage
                    val endIndex = minOf(startIndex + itemsPerPage, movies.size)
                    val moviesSubList = movies.subList(startIndex, endIndex)
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        repeat(moviesSubList.size) { index ->
                            val movie = moviesSubList.getOrNull(index)
                            if (movie != null) {
                                MovieCard(
                                    movie = movie,
                                    modifier = Modifier
                                        .weight(1f),
                                    onClick = { onMovieClick(movie) },
                                    movieCardSizeType = MovieCardSizeType.SMALL
                                )
                            }
                        }

                        val itemsOnLastPage = moviesSubList.size % itemsPerPage
                        val emptyFillerCount =
                            if (itemsOnLastPage == 0) 0 else itemsPerPage - itemsOnLastPage
                        repeat(emptyFillerCount) {
                            Box(modifier = Modifier.weight(1f)) {} // empty space filler
                        }
                    }
                }

                if (showPagerIndicator) {
                    Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                        PagerIndicator(pagerState)
                    }
                }
            }
        }
    }
}