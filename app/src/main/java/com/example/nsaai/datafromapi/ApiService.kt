package com.example.nsaai.datafromapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private val retrofit=Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build()
val movieService= retrofit.create(ApiService::class.java)


interface ApiService {

//    @GET("movie/changes?page=50")
//    suspend fun getMovieData(): MovieData
    @GET("trending/movie/day?language=en-US")
    suspend fun getTrendingMovies(
        @Header("Authorization") authToken: String
    ): MovieData

    @GET("genre/movie/list?language=en")
    suspend fun getGenreData(
        @Header("Authorization") authToken: String
    ): GenreData

}