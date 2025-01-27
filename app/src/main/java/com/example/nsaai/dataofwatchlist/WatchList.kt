package com.example.nsaai.dataofwatchlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "WatchList-table")
data class WatchList(
    @PrimaryKey(autoGenerate = true)
    val id:Long=  0L,
    @ColumnInfo(name = "movie_id")
    val movie_id:Int,
    @ColumnInfo(name="is_selected")
    val isSelected:Boolean=false
)