package com.example.nsaai.dataofwatchlist

import android.app.Application

class WatchListApp:Application() {
    override fun onCreate(){

        super.onCreate()
        Graph.provide(this)

    }
}