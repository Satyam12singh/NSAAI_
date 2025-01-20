package com.example.nsaai.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nsaai.BuildConfig
import com.example.nsaai.datafromapi.ExternalIds
import com.example.nsaai.datafromapi.MovieData
import com.example.nsaai.datafromapi.MovieResult
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

    private val _externalId = mutableStateOf("")
    var externalId= _externalId

    // Automatically fetch movies when the ViewModel initializes
    init {
        fetchMovies()
    }

//    fun fetchMovies() {
//        viewModelScope.launch {
//            try {
//                val response = fetchTrendingMovies()
//                val movieData = Gson().fromJson(response, MovieData::class.java)
//                _movieState.value = _movieState.value.copy(
//                    list = movieData.results,
//                    loading = false,
//                    error = null
//                )
//            } catch (e: Exception) {
//                _movieState.value = _movieState.value.copy(
//                    loading = false,
//                    error = e.message
//                )
//            }
//        }
//    }

    // Using OkHttpClient for the API call with Bearer Token
//    private suspend fun fetchTrendingMovies(): String {
//        val client = OkHttpClient()
//
//
//        val request = Request.Builder()
////            .url("https://api.themoviedb.org/3/trending/movie/day?language=en-US")
//            .url("https://api.themoviedb.org/3/discover/movie?include_adult=true&include_video=true&language=en-US&page=1&sort_by=popularity.desc")
//            .get()
//            .addHeader("accept", "application/json")
//            .addHeader("Authorization", "Bearer $apiKey")  // Using the apiKey variable correctly
//            .build()
//
//        return withContext(Dispatchers.IO) {
//            val response: Response = client.newCall(request).execute()
//            if (!response.isSuccessful) {
//                throw Exception("HTTP Error: ${response.code}") // Better error handling
//            }
//            response.body?.string() ?: throw Exception("Empty Response")
//        }
//    }

    fun fetchMovies() {
        viewModelScope.launch {
            try {
                val allMovies = mutableListOf<MovieResult>()
                for (page in 1..10) { // Fetch all 10 pages
                    val response = fetchTrendingMovies(page)
                    val movieData = Gson().fromJson(response, MovieData::class.java)
                    allMovies.addAll(movieData.results)
                }
                _movieState.value = _movieState.value.copy(
                    list = allMovies,
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

    private suspend fun fetchTrendingMovies(page: Int): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/discover/movie?include_adult=true&include_video=true&language=en-US&page=$page&sort_by=popularity.desc")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $apiKey") // Using the apiKey variable correctly
            .build()

        return withContext(Dispatchers.IO) {
            val response: Response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception("HTTP Error: ${response.code}") // Better error handling
            }
            response.body?.string() ?: throw Exception("Empty Response")
        }
    }
    private suspend fun findexternalids(movieid: Int): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/$movieid/external_ids")  // Without api_key in URL
            .get()
            .addHeader("Authorization", "Bearer $apiKey")  // Pass API key in the Authorization header
            .build()

        return withContext(Dispatchers.IO) {
            val response: Response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception("HTTP Error: ${response.code}")  // Better error handling
            }
            response.body?.string() ?: throw Exception("Empty Response")
        }
    }



    fun fetchExternalIds(movieid: Int) {
        viewModelScope.launch {
            try {
                val response = findexternalids(movieid)
                val externalidResponse = Gson().fromJson(response, ExternalIds::class.java)

                _externalId.value= externalidResponse.imdb_id

                Log.d("External_ID", "IMDB ID: ${externalidResponse.imdb_id}")

            } catch (e: Exception) {
                Log.d("External_ID", "Error: ${e.message}")
            }
        }
    }



    data class MovieState(
        val loading: Boolean = true,
        val list: List<MovieResult> = emptyList(),
        val error: String? = null
    )
}
