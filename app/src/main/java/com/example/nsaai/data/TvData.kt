package com.example.nsaai.data

data class TvData(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)