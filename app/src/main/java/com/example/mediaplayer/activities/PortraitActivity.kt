package com.example.mediaplayer.activities

import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.mediaplayer.services.MusicService
import com.example.mediaplayer.R
import com.example.mediaplayer.tools.Tools
import kotlinx.android.synthetic.main.activity_portrait.*

/**
 * Main class that executes the principal functions of MetalPlayer
 */
class PortraitActivity : AppCompatActivity() {
    lateinit var myService: MusicService
    var isBound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portrait)
        val tools = Tools()
        val songDescription = findViewById<TextView>(R.id.song_info)
        val imageSong = findViewById<ImageView>(R.id.song_image)
        val playButton = findViewById<ImageView>(R.id.play_button)
        val pauseButton = findViewById<ImageView>(R.id.pause_button)
        val backWard = findViewById<ImageView>(R.id.backward_button)
        val fordWard = findViewById<ImageView>(R.id.forward_button)
        val musicServiceIntent = Intent(
            this,
            MUSIC_SERVICE_JAVA_CLASS
        )
        var songPlaying = 0

        bindService(musicServiceIntent, myConnection, Context.BIND_AUTO_CREATE)

        playButton.setOnClickListener {
            myService.startService(musicServiceIntent)
            imageSong.setImageResource(myService.playingSongImage())
            songPlaying = myService.songs[myService.position]
            songDescription.text = tools.songName(resources.getResourceEntryName(songPlaying))
        }
        pauseButton.setOnClickListener {
            myService.stopMusicFunction()
        }
        backWard.setOnClickListener {
            myService.backwardFunction()
            imageSong.setImageResource(myService.playingSongImage())
            songPlaying = myService.songs[myService.position]
            songDescription.text = tools.songName(resources.getResourceEntryName(songPlaying))
        }
        fordWard.setOnClickListener {
            myService.forwardFunction()
            imageSong.setImageResource(myService.playingSongImage())
            songPlaying = myService.songs[myService.position]
            songDescription.text = tools.songName(resources.getResourceEntryName(songPlaying))
        }
        imageSong.setOnClickListener {
            val detailClassIntent = Intent(
                this,
                DETAIL_JAVA_CLASS
            )
            val bundle = Bundle()
            bundle.putInt(VARIABLE_NAME_KEY, songPlaying)
            detailClassIntent.putExtras(bundle)
            startActivity(detailClassIntent)
        }
    }

    /**
     * MyConnection checks the status of the service connection
     */
    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicService.MyBinder
            this@PortraitActivity.myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }

    override fun onDestroy() {
        myService.stopService(
            Intent(
                this,
                MUSIC_SERVICE_JAVA_CLASS
            )
        )
        super.onDestroy()
    }

    companion object {
        const val VARIABLE_NAME_KEY = "songId"
        val PORTRAIT_JAVA_CLASS = PortraitActivity::class.java
        val MUSIC_SERVICE_JAVA_CLASS = MusicService::class.java
        val DETAIL_JAVA_CLASS = DetailSection::class.java
    }
}
