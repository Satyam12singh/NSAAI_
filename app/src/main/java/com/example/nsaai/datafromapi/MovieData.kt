package com.example.nsaai.datafromapi

data class MovieData(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)
