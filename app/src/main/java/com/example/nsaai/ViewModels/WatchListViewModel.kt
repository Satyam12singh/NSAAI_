package com.example.nsaai.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nsaai.dataofwatchlist.Graph
import com.example.nsaai.dataofwatchlist.WatchList
import com.example.nsaai.dataofwatchlist.WatchListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WatchListViewModel(
    private val repository: WatchListRepository = Graph.watchListRepository

):ViewModel() {


    lateinit var getAllListedMovies: Flow<List<WatchList>>

    init {
        viewModelScope.launch {
            getAllListedMovies = repository.getAllMovies()
        }
    }
    fun addAMovie(WatchListEntity: WatchList){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAMovie(WatchListEntity)
        }
    }
    fun deleteAMovie(WatchListEntity: WatchList){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAMovie(WatchListEntity)
        }
    }


}