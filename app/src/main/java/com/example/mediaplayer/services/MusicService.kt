package com.example.mediaplayer.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.mediaplayer.R

/**
 * MusicService plays the music and keeps playing it
 */
class MusicService : Service() {
    var position =
        INITIAL_POSITION
    val songs = arrayOf(CHOP_RAW_FILE, COCAINE_RAW_FILE, PAINKILLER_RAW_FILE)
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

    fun stopMusicFunction() {
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
            position =
                INITIAL_POSITION
            mediaPlayer = MediaPlayer.create(this, songs[position])
        } else {
            mediaPlayer = MediaPlayer.create(this, songs[position])
        }
        mediaPlayer.start()
    }

    fun playingSongImage(): Int {
        if (position == CHOP_POSITION) {
            return CHOP_DRAWABLE
        }
        if (position == COCAINE_POSITION) {
            return COCAINE_DRAWABLE
        }
        if (position == PAINKILLER_POSITION) {
            return PAINKILLER_DRAWABLE
        }
        return PROVISIONAL_RETURN
    }

    /**
     * MyBinder returns a bing to the service
     */
    inner class MyBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }

    companion object {
        const val PROVISIONAL_RETURN = 0
        const val CHOP_POSITION = 0
        const val COCAINE_POSITION = 1
        const val PAINKILLER_POSITION = 2
        const val CHOP_RAW_FILE = R.raw.chop_suey
        const val COCAINE_RAW_FILE = R.raw.cocaine
        const val PAINKILLER_RAW_FILE = R.raw.painkiller
        const val CHOP_DRAWABLE = R.drawable.icons_metal_player_chop_suey
        const val COCAINE_DRAWABLE = R.drawable.icons_metal_player_cocaine
        const val PAINKILLER_DRAWABLE = R.drawable.icons_metal_player_painkiller
        const val INITIAL_POSITION = 0
        const val CONSTANT_RECEIVER = 1
    }
}