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
import com.example.nsaai.TrailerFromApi.Result
import com.example.nsaai.TrailerFromApi.Trailer
import com.example.nsaai.datafromapi.ExternalIds
import com.example.nsaai.datafromapi.MovieResultExternalId
import com.example.nsaai.datafromapi.MovieResultX

import com.google.android.play.integrity.internal.f
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject


class AboutMovieViewModel : ViewModel()
{

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
    private val normalapiKey= BuildConfig.api_key_normal

    private val _key= mutableStateOf("")
    val key=_key

    private val _istrailerornot= mutableStateOf("")
    val trailerornot= _istrailerornot

    private val _listofvideolinks = mutableStateOf<List<Result>>(listOf())
    val listofresultlinks = _listofvideolinks

    // Fetch basic movie details
    suspend fun fetchAboutTheMovie(id: String,onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = fetchAboutMovie(id)
                Log.d("AboutMovieViewModel", "API Response: $response")

                val aboutMovie = Gson().fromJson(response, MovieResultX::class.java)
                val movieResult = aboutMovie.movie_results.firstOrNull()

                if (movieResult != null) {
                    _posterPath.value = movieResult.poster_path ?: "No poster available"
                    _imageofmovie.value = movieResult.backdrop_path ?: ""
                    _title.value = movieResult.title
                }
            } catch (e: Exception) {
                Log.e("fetchAboutTheMovie", "Error: ${e.message}")
            }
        }
        onComplete()
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
    suspend fun fetchCastDetails(id: Int,onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = fetchCastDetailsFromApi(id)
                val crewMembers = Gson().fromJson(response, CrewMembers::class.java)
                _cast.value = crewMembers.cast
            } catch (e: Exception) {
                Log.e("fetchCastDetails", "Error: ${e.message}")
            }
        }
        onComplete()
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
    suspend fun fetchAllMovieDetails(id: Int,onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
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
        onComplete()
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

    suspend fun fetchyoutubetrailerid(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response= fetchYoutubeTrailer(id)
                val aboutyoutubedata= Gson().fromJson(response,Trailer::class.java)

//                _key.value= aboutyoutubedata.key
//                _istrailerornot.value=aboutyoutubedata.type
                _listofvideolinks.value=aboutyoutubedata.results

            }catch (e: Exception){
                Log.e("fetchyoutubetrailerid", "Error: ${e.message}")
            }

        }
    }

    private suspend fun fetchYoutubeTrailer(id: Int): String {
        val client=OkHttpClient()
        val request= Request.Builder()
            .url("https://api.themoviedb.org/3/movie/${id}/videos?api_key=${normalapiKey}")
            .get()
            .build()

        return withContext(Dispatchers.IO){
            val response=client.newCall(request).execute()
            if(!response.isSuccessful){
                throw Exception("HTTP Error: ${response.code}")
            }
            response.body?.string() ?: throw Exception("Empty Response")
        }
    }

//    fun isMovieInDatabase(movieId: Int): Flow<Boolean> =
//        repository.getMovieById(movieId).map { it != null }
//
//
//    fun storeMovieToDatabase(movieId: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addAMovie(WatchList(movie_id = movieId))
//        }
//    }
//
//    fun deleteMovieFromDatabase(movieId: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteAMovie(WatchList(movie_id = movieId))
//        }
//    }



}
