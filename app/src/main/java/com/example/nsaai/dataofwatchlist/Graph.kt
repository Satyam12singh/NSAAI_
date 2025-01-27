package com.example.nsaai.dataofwatchlist

import android.content.Context
import androidx.room.Room

object Graph {

    lateinit var database: WatchListDatabase
        private set
    val watchListRepository by lazy {
        WatchListRepository(watchListDao = database.watchListDao())
    }
    fun provide(context: Context){
        database = Room.databaseBuilder(context ,WatchListDatabase::class.java,"watchlist.db").build()
    }
}
