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
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portrait)
        var position = 0
        val songs = arrayOf(R.raw.chop_suey, R.raw.cocaine, R.raw.painkiller)
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
            /*            val t = Toast.makeText(
                            this,
                            mediaPlayer.selectTrack(2).toString(),
                            Toast.LENGTH_SHORT
                        )
                        t.show()*/
            mediaPlayer.pause()
        }
        backWard.setOnClickListener {
            position--
            mediaPlayer.stop()
            if (position < 0) {
                position = songs.size - 1
                mediaPlayer = MediaPlayer.create(this, songs[position])
            } else {
                mediaPlayer = MediaPlayer.create(this, songs[position])
            }
            mediaPlayer.start()
        }
        fordWard.setOnClickListener {
            mediaPlayer.stop()
            position++
            if (position == songs.size) {
                position = 0
                mediaPlayer = MediaPlayer.create(this, songs[position])
            } else {
                mediaPlayer = MediaPlayer.create(this, songs[position])
            }
            mediaPlayer.start()
        }

    }

}
