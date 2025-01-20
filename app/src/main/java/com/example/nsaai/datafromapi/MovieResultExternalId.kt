package com.example.nsaai.datafromapi

data class MovieResultExternalId(
    val movie_results: List<MovieResultX>,
    val person_results: List<Any?>,
    val tv_episode_results: List<Any?>,
    val tv_results: List<Any?>,
    val tv_season_results: List<Any?>
)