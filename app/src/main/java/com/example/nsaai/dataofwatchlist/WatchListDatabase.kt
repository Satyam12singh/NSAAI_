package com.example.nsaai.dataofwatchlist

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [WatchList::class],
    version = 1,
    exportSchema = false
)
abstract class WatchListDatabase :RoomDatabase() {

    abstract fun watchListDao(): WatchListDao

}