package com.example.nsaai.dataofwatchlist

import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow

class WatchListRepository(
    private val watchListDao: WatchListDao
) {


    suspend fun getAllMovies(): Flow<List<WatchList>> = watchListDao.getAllmoviesofwatchlist()

    fun addAMovie(WatchListEntity: WatchList){
            watchListDao.addAMovie(WatchListEntity)

    }

    fun deleteAMovie(WatchListEntity: WatchList){
            watchListDao.deleteAMovie(WatchListEntity)
    }

}