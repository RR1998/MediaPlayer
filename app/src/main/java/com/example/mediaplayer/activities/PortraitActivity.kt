package com.example.mediaplayer.activities

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
import com.example.mediaplayer.R
import com.example.mediaplayer.services.MusicService
import com.example.mediaplayer.tools.Tools
import kotlinx.android.synthetic.main.activity_portrait.*

/**
 * Main class that executes the principal functions of MetalPlayer
 */
class PortraitActivity : AppCompatActivity() {
    lateinit var myService: MusicService
    var isBound = false
    private val tools = Tools()
    /**
     * MyConnection checks the status of the service connection
     */
    private val session = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicService.MyBinder
            this@PortraitActivity.myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portrait)
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
        var songPlaying = INITIAL_SONG_DEFAULT_VALUE
        bindService(musicServiceIntent, session, Context.BIND_AUTO_CREATE)
        playButton.setOnClickListener {
            myService.startService(musicServiceIntent)
            imageSong.setImageResource(myService.playingSongImage())
            songPlaying = myService.songs[myService.position]
            songDescription.text = tools.songName(resources.getResourceEntryName(songPlaying))
            createNotificationChannel()
        }
        pauseButton.setOnClickListener {
            myService.stopMusicFunction()
        }
        backWard.setOnClickListener {
            myService.backwardFunction()
            imageSong.setImageResource(myService.playingSongImage())
            songPlaying = myService.songs[myService.position]
            songDescription.text = tools.songName(resources.getResourceEntryName(songPlaying))
            createNotificationChannel()
        }
        fordWard.setOnClickListener {
            myService.forwardFunction()
            imageSong.setImageResource(myService.playingSongImage())
            songPlaying = myService.songs[myService.position]
            songDescription.text = tools.songName(resources.getResourceEntryName(songPlaying))
            createNotificationChannel()
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

    override fun onDestroy() {
        val context: Context = this
        myService.stopService(
            Intent(
                context,
                MUSIC_SERVICE_JAVA_CLASS
            )
        )
        super.onDestroy()
    }

    /**
     * Creates a notification and add to it an action button
     */
    private fun createNotificationChannel() {
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(applicationContext, PORTRAIT_JAVA_CLASS)
        val pendingIntent = PendingIntent.getActivity(
            this, REQUEST_CODE, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val descriptionText = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, name,
                importance
            ).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val snoozeIntent = Intent(this, PORTRAIT_JAVA_CLASS).apply {
            putExtra(PAUSE_BUTTON, 0)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(
            this,
            REQUEST_CODE, snoozeIntent
            , FLAGS
        )
        val builder = NotificationCompat.Builder(
            this,
            NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(PLAYER_ICON)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(song_info.text.toString())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .addAction(PLAYER_ICON, PLAY_BUTTON, snoozePendingIntent)
            .addAction(PAUSE_ICON, PAUSE_BUTTON, snoozePendingIntent)
        builder.setContentIntent(pendingIntent)
        mNotificationManager.notify(NOTIFICATION_MANAGER_ID, builder.build())
    }

    companion object {
        const val INITIAL_SONG_DEFAULT_VALUE = 0
        const val NOTIFICATION_MANAGER_ID = 0
        const val FLAGS = 0
        const val REQUEST_CODE = 0
        const val PLAY_BUTTON = "Play"
        const val PAUSE_BUTTON = "Pause"
        const val NOTIFICATION_TITLE = "Playing Music"
        const val NOTIFICATION_CHANNEL_ID = "CHANNEL_ID"
        const val VARIABLE_NAME_KEY = "songId"
        const val PLAYER_ICON = R.drawable.icons_metal_player_play
        const val PAUSE_ICON = R.drawable.icons_metal_player_pause
        val PORTRAIT_JAVA_CLASS = PortraitActivity::class.java
        val MUSIC_SERVICE_JAVA_CLASS = MusicService::class.java
        val DETAIL_JAVA_CLASS = DetailSection::class.java
    }
}
