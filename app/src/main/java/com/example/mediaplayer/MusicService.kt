package com.example.mediaplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

/**
 * MusicService plays the music and keeps playing it
 */
class MusicService : Service() {
    var position = INITIAL_POSITION
    val songs = arrayOf(R.raw.chop_suey, R.raw.cocaine, R.raw.painkiller)
    private val myBinder = MyBinder()
    private lateinit var mediaPlayer: MediaPlayer
    override fun onBind(intent: Intent?): IBinder? {
        return myBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        mediaPlayer = MediaPlayer.create(this, songs[position])
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.pause()
    }

    /**
     * backward function retreats one position in the songs array
     */
    fun backwardFunction() {
        position--
        mediaPlayer.stop()
        if (position < INITIAL_POSITION) {
            position = songs.size - CONSTANT_RECEIVER
            mediaPlayer = MediaPlayer.create(this, songs[position])
        } else {
            mediaPlayer = MediaPlayer.create(this, songs[position])
        }
        mediaPlayer.start()
    }

    /**
     * forward function advance one position in the songs array
     */
    fun forwardFunction() {
        mediaPlayer.stop()
        position++
        if (position == songs.size) {
            position = INITIAL_POSITION
            mediaPlayer = MediaPlayer.create(this, songs[position])
        } else {
            mediaPlayer = MediaPlayer.create(this, songs[position])
        }
        mediaPlayer.start()
    }

    inner class MyBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }

    companion object {
        const val INITIAL_POSITION = 0
        const val CONSTANT_RECEIVER = 1
    }
}