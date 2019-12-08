package com.example.mediaplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Main class that executes the principal functions of MetalPlayer
 */
class PortraitActivity : AppCompatActivity() {
    lateinit var myService: MusicService
    var isBound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portrait)
        val imageDescription = findViewById<TextView>(R.id.song_info)
        val imageSong = findViewById<ImageView>(R.id.song_image)
        val pruebaButton = findViewById<Button>(R.id.prueba)
        val playButton = findViewById<ImageView>(R.id.play_button)
        val pauseButton = findViewById<ImageView>(R.id.pause_button)
        val backWard = findViewById<ImageView>(R.id.backward_button)
        val fordWard = findViewById<ImageView>(R.id.forward_button)
        val musicServiceClass = MusicService::class.java
        bindService(Intent(this, musicServiceClass), myConnection, Context.BIND_AUTO_CREATE)
        playButton.setOnClickListener {
            startService(Intent(this, musicServiceClass))
        }
        pauseButton.setOnClickListener {
            stopService(Intent(this, musicServiceClass))
        }
        backWard.setOnClickListener {
            myService.backwardFunction()
        }
        fordWard.setOnClickListener {
            myService.forwardFunction()
        }
        pruebaButton.setOnClickListener {
            val intent = Intent(this, musicServiceClass)
            startActivity(intent)
        }
    }
    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName,  service: IBinder) {
            val binder = service as MusicService.MyBinder
            this@PortraitActivity.myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, MusicService::class.java))
        super.onDestroy()
    }
}
