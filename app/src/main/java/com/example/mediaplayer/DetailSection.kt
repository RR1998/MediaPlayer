package com.example.mediaplayer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailSection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val tools = Tools()
        val song = findViewById<TextView>(R.id.band_name)
        val infoSong = findViewById<TextView>(R.id.song_info)
        val bundleReceived = intent.extras
        val id = bundleReceived!!.getInt("songId")
        song.text = tools.songName(resources.getResourceEntryName(id))
    }
}