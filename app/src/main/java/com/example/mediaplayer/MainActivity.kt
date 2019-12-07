package com.example.mediaplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Main class that executes the principal functions of MetalPlayer
 */
class MainActivity : AppCompatActivity() {
    var position = INITIAL_POSITION
    private val songs = arrayOf(R.raw.chop_suey, R.raw.cocaine, R.raw.painkiller)
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portrait)
        val imageDescription = findViewById<TextView>(R.id.song_info)
        val imageSong = findViewById<ImageView>(R.id.song_image)
        val playButton = findViewById<ImageView>(R.id.play_button)
        val pauseButton = findViewById<ImageView>(R.id.pause_button)
        val backWard = findViewById<ImageView>(R.id.backward_button)
        val fordWard = findViewById<ImageView>(R.id.forward_button)
        mediaPlayer = MediaPlayer.create(this, songs[position])
        playButton.setOnClickListener {
            mediaPlayer.start()
        }
        pauseButton.setOnClickListener {
            mediaPlayer.pause()
        }
        backWard.setOnClickListener {
            backwardFunction()
        }
        fordWard.setOnClickListener {
            forwardFunction()
        }
    }
    private fun backwardFunction(){
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
    private fun forwardFunction(){
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

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return (mediaPlayer)

    }

    companion object {
        const val INITIAL_POSITION = 0
        const val CONSTANT_RECEIVER = 1
    }
}
