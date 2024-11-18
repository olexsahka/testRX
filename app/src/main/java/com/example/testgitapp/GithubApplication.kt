package com.example.testgitapp

import android.app.Application
import com.example.testgitapp.data.local.database.DetailDatabase

class GithubApplication: Application() {

    companion object {
        // TODO NEED DI
        // TODO BE CARE
        lateinit var database: DetailDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = DetailDatabase.getInstance(applicationContext)
    }
}