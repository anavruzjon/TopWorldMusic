package com.nakhmadov.topworldmusic.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import kotlin.Exception

class MusicService(
    private var url: String?,
    private var firstStart: Boolean = true,
    private var player: MediaPlayer?
) : Service() {

    companion object {
        lateinit var instance: MusicService
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        instance = this
        url = intent?.getStringExtra("url")
        play()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (player != null) {
            player?.reset()
            player?.release()
            player = null
        }
    }

    fun pause() {
        if (player?.isPlaying!!) {
            player?.pause()
        }
    }

    fun stop() {
        if (player?.isPlaying!!) {
            player?.stop()
            player?.reset()
            firstStart = true
        }
    }

    fun play() {
        if (firstStart) {
            try {
                player?.setDataSource(url)
                player?.setOnCompletionListener {
                    player?.stop()
                    player?.reset()
                }

                player?.prepare()
                player?.start()
                firstStart = false
            } catch (e: Exception) {
                Log.d("myLogs", "Music PLAY ERROR ${e.localizedMessage}")
            }
        } else {
            if (player?.isPlaying!!)
                player?.start()
        }
    }
}