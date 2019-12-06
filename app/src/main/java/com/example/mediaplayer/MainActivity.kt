package com.example.mediaplayer

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val playButton = findViewById<Button>(R.id.play_button)
        val pauseButton = findViewById<Button>(R.id.pause_button)
        val stopButton = findViewById<Button>(R.id.stop_button)
        val resetButton = findViewById<Button>(R.id.resume_button)
        mediaPlayer = MediaPlayer.create(this ,R.raw.chop_suey)
        playButton.setOnClickListener {
            //val intent = Intent(this, NumbersActivity::class.java)
            mediaPlayer?.start()
            //startActivity(intent)
            var t = Toast.makeText(this, mediaPlayer.duration.toString(), Toast.LENGTH_SHORT)
            t.show()
        }
        pauseButton.setOnClickListener {
            mediaPlayer?.pause()
        }
        stopButton.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer = MediaPlayer.create(this ,R.raw.painkiller)
        }
        resetButton.setOnClickListener {
            mediaPlayer?.reset()
        }
        mediaPlayer.setOnCompletionListener {
            //findViewById<raw.>()
        }
    }
}
