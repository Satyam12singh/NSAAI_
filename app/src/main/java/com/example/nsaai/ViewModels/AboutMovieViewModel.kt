package com.example.nsaai.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nsaai.BuildConfig
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

    private val _externalId = mutableStateOf("")
    val externalId = _externalId

    val apiKey = BuildConfig.API_KEY

    fun fetchAboutTheMovie(id: String) {
        viewModelScope.launch {
            try {
                val response = fetchAboutMovie(id)
//                val movieResults = Gson().fromJson(response, ExternalIds::class.java)
                val aboutmovie= Gson().fromJson(response,MovieResultX::class.java)
                _posterPath.value= aboutmovie.poster_path

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
}
