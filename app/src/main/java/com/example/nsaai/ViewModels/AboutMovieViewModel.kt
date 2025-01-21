package com.example.nsaai.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nsaai.BuildConfig
import com.example.nsaai.CastByApi.Cast
import com.example.nsaai.DetailOfMovieApi.DetailOfMovie
import com.example.nsaai.datafromapi.ExternalIds
import com.example.nsaai.datafromapi.MovieResultExternalId
import com.example.nsaai.datafromapi.MovieResultX
import com.google.gson.Gson
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

    val apiKey = BuildConfig.API_KEY

    fun fetchAboutTheMovie(id: String) {
        viewModelScope.launch {
            try {
                val response = fetchAboutMovie(id)
//                val movieResults = Gson().fromJson(response, ExternalIds::class.java)
                Log.d("AboutMovieViewModel", "API Response: $response")
                val aboutmovie= Gson().fromJson(response,MovieResultX::class.java)
                _posterPath.value = aboutmovie.movie_results[0].poster_path ?: "No poster available"
                _imageofmovie.value=aboutmovie.movie_results[0].backdrop_path?:""

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

    fun fetchCastDetails(id:Int){
        viewModelScope.launch {
            try {
                val response = fetchAboutMovie(id)
//                val movieResults = Gson().fromJson(response, ExternalIds::class.java)
                Log.d("AboutMovieViewModel", "API Response: $response")
                val aboutmovie= Gson().fromJson(response,Cast::class.java)


            }catch (e: Exception){
                Log.e("fetchCastDetails", "Error: ${e.message}")
            }
        }
    }

    private suspend fun fetchCastDetails(id: Int): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/${id}/credits?language=en-US")
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

    fun fetchAllMovieDetails(id:Int){
        viewModelScope.launch {
            try {
                val response = fetchAboutMovie(id)
//                val movieResults = Gson().fromJson(response, ExternalIds::class.java)
                Log.d("AboutMovieViewModel", "API Response: $response")
                val aboutmovie= Gson().fromJson(response,DetailOfMovie::class.java)


            }catch (e: Exception){
                Log.e("fetchCastDetails", "Error: ${e.message}")
            }
        }

    }

    private suspend fun fetchAllMovieDetails(id: Int){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/${id}?language=en-US")
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
