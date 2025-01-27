package com.example.nsaai.dataofwatchlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
abstract class WatchListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract  fun addAMovie(WatchListEntity: WatchList)

    @Query("SELECT * FROM `WatchList-table`")
    abstract fun getAllmoviesofwatchlist(): Flow<List<WatchList>>

    @Delete
    abstract fun deleteAMovie(WatchListEntity: WatchList)

}