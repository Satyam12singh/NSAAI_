package com.example.nsaai.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nsaai.ViewModels.WatchListViewModel

@Composable
fun WatchListMovies(
    modifier: Modifier = Modifier,
    viewmodel: WatchListViewModel
) {
    val allMoviesInWatchlist by viewmodel.getAllListedMovies.collectAsState(initial = emptyList())

    if (allMoviesInWatchlist.isEmpty()) {
        Text(
            text = "No movies in your watchlist.",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    } else {
        LazyColumn {
            items(allMoviesInWatchlist) { movie ->
                Text(
                    text = "Movie ID: ${movie.movie_id}",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
