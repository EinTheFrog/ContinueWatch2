package ru.spbstu.icc.kspt.lab2.continuewatch

import android.app.Application
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyApplication : Application() {
    companion object {
        val executor: ExecutorService = Executors.newFixedThreadPool(1)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("Debug", "Application created")
    }
}