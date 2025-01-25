package com.example.nsaai.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nsaai.BuildConfig
import com.example.nsaai.TrailerFromApi.Result
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

    private val _trendmoviestate= mutableStateOf(MovieState())
    val trendmoviestate: State<MovieState> = _trendmoviestate

    private val _upcomingmoviestate= mutableStateOf(MovieState())
    val upcomingmoviestate: State<MovieState> = _upcomingmoviestate

    private val _popularmoviestate= mutableStateOf(MovieState())
    val popularmoviestate:State<MovieState> = _popularmoviestate

    private val _alltrendingmovies = mutableStateOf<List<MovieResult>>(listOf())
    val alltrendingmovies= _alltrendingmovies

    private val _allUpcomingMovies = mutableListOf<MovieResult>()
    val allupcomingMovies= _allUpcomingMovies

    private val _allpopmovies=  mutableStateOf<List<MovieResult>>(listOf())
    val allpopmovies= _allpopmovies
//    private val _alltrendmovie = mutableStateOf<List<Result>>(listOf())
//    val alltrendmovie = _alltrendmovie
//    private val _allpopmovie = mutableStateOf<List<Result>>(listOf())
//    val allpopmovie = _allpopmovie

    private val _externalId = mutableStateOf("")
    var externalId : State<String> = _externalId

    // Automatically fetch movies when the ViewModel initializes
    init {
        fetchMovies()
        fetchTrendingMovies()
        fetchPopularMovies()
        fetchUpcomingMovie()
    }



    fun fetchUpcomingMovie() {
        viewModelScope.launch {
            try {
//                val allUpcomingMovies = mutableListOf<MovieResult>()
                for (page in 1..10) { // Fetch all 10 pages
                    val response = fetchAllMovies(page)
                    val UpcomingmovieData = Gson().fromJson(response, MovieData::class.java)
                    _allUpcomingMovies.addAll(UpcomingmovieData.results)
                }

                _upcomingmoviestate.value = _upcomingmoviestate.value.copy(
                    list = _allUpcomingMovies,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _upcomingmoviestate.value = _upcomingmoviestate.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    private suspend fun fetchAllUpcomingMovie(page: Int): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=${page}")
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




    fun fetchTrendingMovies() {
        viewModelScope.launch {
            try {

                val allMovies = mutableListOf<MovieResult>()
                val response = FetchTrendingMovie()
                val trendmovieData = Gson().fromJson(response, MovieData::class.java)

//                _alltrendingmovies.clear()
                _alltrendingmovies.value = (trendmovieData.results)
                _trendmoviestate.value = _trendmoviestate.value.copy(
                    list = allMovies,
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _trendmoviestate.value = _trendmoviestate.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    private suspend fun FetchTrendingMovie():String{
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/trending/movie/day?language=en-US")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        return withContext(Dispatchers.IO) {
            val response: Response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception("HTTP Error: ${response.code}") // Better error handling
            }
            response.body?.string() ?: throw Exception("Empty Response")
        }

    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            try{

                val allMovies = mutableListOf<MovieResult>()
                val response = FetchPopularMovies()
                val popMoviedata = Gson().fromJson(response, MovieData::class.java)

//                _allpopmovies.clear()
                _allpopmovies.value= (popMoviedata.results)

                _popularmoviestate.value = _popularmoviestate.value.copy(
                    list = popMoviedata.results,
                    loading = false,
                    error = null
                )

            }catch (e:Exception){
                _popularmoviestate.value=_popularmoviestate.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    private suspend fun FetchPopularMovies():String{
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/trending/movie/week?language=en-US")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        return withContext(Dispatchers.IO) {
            val response: Response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception("HTTP Error: ${response.code}") // Better error handling
            }
            response.body?.string() ?: throw Exception("Empty Response")
        }

    }



    fun fetchMovies() {
        viewModelScope.launch {
            try {
                val allMovies = mutableListOf<MovieResult>()
                for (page in 1..10) { // Fetch all 10 pages
                    val response = fetchAllMovies(page)
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

    private suspend fun fetchAllMovies(page: Int): String {
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
                Log.d("exfetchfor aboutmnovie","${externalidResponse.imdb_id}")

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
