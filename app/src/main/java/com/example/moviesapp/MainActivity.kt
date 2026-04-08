package com.example.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesapp.presentation.home.HomeScreen
import com.example.moviesapp.presentation.movie_details.MovieDetailsScreen
import com.example.moviesapp.presentation.navigation.BottomNavItem
import com.example.moviesapp.presentation.navigation.Screen
import com.example.moviesapp.presentation.search.SearchMoviesScreen
import com.example.moviesapp.presentation.watchlist_movies.WatchlistMoviesScreen
import com.example.moviesapp.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoviesAppTheme {
                val navController = rememberNavController()

                val items = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Search,
                    BottomNavItem.Watchlist
                )

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            val currentRoute =
                                navController.currentBackStackEntry?.destination?.route

                            items.forEach { item ->
                                NavigationBarItem(
                                    icon = { Icon(item.icon, contentDescription = item.label) },
                                    label = { Text(item.label) },
                                    selected = currentRoute == item.route,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(0) //clears entire back stack
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { padding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
                    ) {

                        composable(Screen.Home.route) {
                            HomeScreen(navController = navController)
                        }

                        composable(Screen.Search.route) {
                            SearchMoviesScreen(navController = navController)
                        }

                        composable(Screen.Watchlist.route) {
                            WatchlistMoviesScreen(navController = navController)
                        }

                        composable(
                            Screen.MovieDetails.route,
                            arguments = listOf(
                                navArgument("id") { type = NavType.IntType }
                            )
                        ) {
                            MovieDetailsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}