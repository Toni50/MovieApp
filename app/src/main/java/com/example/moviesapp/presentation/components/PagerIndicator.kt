package com.example.moviesapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicator(pagerState: PagerState) {
    Row(
        modifier = Modifier
            .background(color = Color.Black.copy(0.5f), shape = RoundedCornerShape(20.dp))
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { index ->
            val isSelected = pagerState.currentPage == index
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .padding(5.dp)
                    .background(
                        color = if (isSelected) Color.DarkGray else Color.LightGray,
                        shape = CircleShape
                    )
            ) {
            }
        }
    }
}