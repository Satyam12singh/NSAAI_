package com.example.nsaai.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nsaai.BuildConfig
import com.example.nsaai.datafromapi.Genre
import com.example.nsaai.datafromapi.GenreData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class GenreViewModel : ViewModel() {

    private val apiKey = BuildConfig.API_KEY
    private val _genreState = mutableStateOf(GenreState())
    val genrestate: State<GenreState> = _genreState
    init {
        fetchGenre()
    }

    fun fetchGenre() {
        viewModelScope.launch {
            try {
                val response = fetchGenreofMovies()
                val genreData = Gson().fromJson(response, GenreData::class.java)
                _genreState.value = _genreState.value.copy(
                    list = genreData.genres,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _genreState.value = _genreState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    private suspend fun fetchGenreofMovies(): String {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/genre/movie/list?language=en")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        return withContext(Dispatchers.IO) {
            val response: Response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception("HTTP Error: ${response.code}")
            }
            response.body?.string() ?: throw Exception("Empty Response")
        }
    }


    data class GenreState(
        val loading: Boolean = true,
        val list: List<Genre> = emptyList(),
        val error: String? = null
    )
}