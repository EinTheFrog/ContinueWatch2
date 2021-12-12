package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.RejectedExecutionException

class MainActivityExecutorService : AppCompatActivity() {
    var secondsElapsed: Int = 0
    private var secondsElapsedBeforeStop = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var future: Future<*>

    companion object {
        const val STATE_SECONDS = "secondsElapsed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        with(savedInstanceState) { secondsElapsedBeforeStop = getInt(STATE_SECONDS) }
        secondsElapsed = secondsElapsedBeforeStop
        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStart() {
        future = initBackgroundThread()
        super.onStart()
    }

    override fun onStop() {
        future.cancel(true)
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        secondsElapsedBeforeStop = secondsElapsed
        outState.run {
            putInt(STATE_SECONDS, secondsElapsedBeforeStop)
        }
        super.onSaveInstanceState(outState)
    }

    private fun initBackgroundThread(): Future<*> {
        return MyApplication.executor.submit {
            while(!MyApplication.executor.isShutdown) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed++)
                }
            }
        }
    }
}