package com.example.nsaai.ViewModels

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nsaai.BuildConfig
import com.example.nsaai.R
import com.example.nsaai.data.MovieData
import com.example.nsaai.data.MovieResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class MovieViewModel : ViewModel() {



    private val apiKey =  BuildConfig.API_KEY
    private val _movieState = mutableStateOf(MovieState())
    val moviestate: State<MovieState> = _movieState

    // Automatically fetch movies when the ViewModel initializes
    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            try {
                val response = fetchTrendingMovies()
                val movieData = Gson().fromJson(response, MovieData::class.java)
                _movieState.value = _movieState.value.copy(
                    list = movieData.results,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _movieState.value = _movieState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    // Using OkHttpClient for the API call with Bearer Token
    private suspend fun fetchTrendingMovies(): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/trending/movie/day?language=en-US")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")  // Using the apiKey variable correctly
            .build()

        return withContext(Dispatchers.IO) {
            val response: Response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception("HTTP Error: ${response.code}") // Better error handling
            }
            response.body?.string() ?: throw Exception("Empty Response")
        }
    }

    data class MovieState(
        val loading: Boolean = true,
        val list: List<MovieResult> = emptyList(),
        val error: String? = null
    )
}
