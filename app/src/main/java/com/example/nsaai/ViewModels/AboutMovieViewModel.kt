package com.example.nsaai.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nsaai.BuildConfig
import com.example.nsaai.CastByApi.Cast
import com.example.nsaai.CastByApi.CrewMembers
import com.example.nsaai.DetailOfMovieApi.DetailOfMovie
import com.example.nsaai.DetailOfMovieApi.Genre
import com.example.nsaai.datafromapi.ExternalIds
import com.example.nsaai.datafromapi.MovieResultExternalId
import com.example.nsaai.datafromapi.MovieResultX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


import okhttp3.OkHttpClient
import okhttp3.Request


class AboutMovieViewModel : ViewModel() {

    private val _posterPath = mutableStateOf("")
    val posterPath = _posterPath

    private val _imageofmovie = mutableStateOf("")
    val imageofmovie = _imageofmovie

    private val _externalId = mutableStateOf("")
    val externalId = _externalId

    private val _title = mutableStateOf("")
    val title = _title

    private val _adult = mutableStateOf(false)
    val adult = _adult

    private val _original_language = mutableStateOf("")
    val original_language = _original_language

    private val _vote_average = mutableStateOf(0.0)
    val vote_average = _vote_average

    private val _overview = mutableStateOf("")
    val overview = _overview

    private val _release_date = mutableStateOf("")
    val release_date = _release_date

    private val _runtime = mutableStateOf(0)
    val runtime = _runtime

    private val _cast = mutableStateOf<List<Cast>>(listOf())
    val cast = _cast

    private val apiKey = BuildConfig.API_KEY

    // Fetch basic movie details
    fun fetchAboutTheMovie(id: String) {
        viewModelScope.launch {
            try {
                val response = fetchAboutMovie(id)
                Log.d("AboutMovieViewModel", "API Response: $response")

                val aboutMovie = Gson().fromJson(response, MovieResultX::class.java)
                val movieResult = aboutMovie.movie_results.firstOrNull()

                if (movieResult != null) {
                    _posterPath.value = movieResult.poster_path ?: "No poster available"
                    _imageofmovie.value = movieResult.backdrop_path ?: ""
                }
            } catch (e: Exception) {
                Log.e("fetchAboutTheMovie", "Error: ${e.message}")
            }
        }
    }

    private suspend fun fetchAboutMovie(id: String): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/find/$id?external_source=imdb_id")
            .get()
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("accept", "application/json")
            .build()

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception("HTTP Error: ${response.code}")
            }
            response.body?.string() ?: throw Exception("Empty Response")
        }
    }

    // Fetch cast details
    fun fetchCastDetails(id: Int) {
        viewModelScope.launch {
            try {
                val response = fetchCastDetailsFromApi(id)
                val crewMembers = Gson().fromJson(response, CrewMembers::class.java)
                _cast.value = crewMembers.cast
            } catch (e: Exception) {
                Log.e("fetchCastDetails", "Error: ${e.message}")
            }
        }
    }

    private suspend fun fetchCastDetailsFromApi(id: Int): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/$id/credits?language=en-US")
            .get()
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("accept", "application/json")
            .build()

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception("HTTP Error: ${response.code}")
            }
            response.body?.string() ?: throw Exception("Empty Response")
        }
    }

    // Fetch all movie details
    fun fetchAllMovieDetails(id: Int) {
        viewModelScope.launch {
            try {
                val response = fetchAllMovieDetailsFromApi(id)
                Log.d("AboutMovieViewModel", "API Response: $response")

                val movieDetails = Gson().fromJson(response, DetailOfMovie::class.java)
                _title.value = movieDetails.title
                _adult.value = movieDetails.adult
                _original_language.value = movieDetails.original_language
                _vote_average.value = movieDetails.vote_average
                _overview.value = movieDetails.overview
                _release_date.value = movieDetails.release_date
                _runtime.value = movieDetails.runtime
            } catch (e: Exception) {
                Log.e("fetchAllMovieDetails", "Error: ${e.message}")
            }
        }
    }

    private suspend fun fetchAllMovieDetailsFromApi(id: Int): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/$id?language=en-US")
            .get()
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("accept", "application/json")
            .build()

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception("HTTP Error: ${response.code}")
            }
            response.body?.string() ?: throw Exception("Empty Response")
        }
    }
}
