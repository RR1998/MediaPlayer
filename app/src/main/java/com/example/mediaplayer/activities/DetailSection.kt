package com.example.mediaplayer.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mediaplayer.R
import com.example.mediaplayer.activities.PortraitActivity.Companion.VARIABLE_NAME_KEY
import com.example.mediaplayer.services.MusicService.Companion.CHOP_SUEY_DRAWABLE
import com.example.mediaplayer.services.MusicService.Companion.CHOP_SUEY_RAW_FILE
import com.example.mediaplayer.services.MusicService.Companion.COCAINE_DRAWABLE
import com.example.mediaplayer.services.MusicService.Companion.COCAINE_RAW_FILE
import com.example.mediaplayer.services.MusicService.Companion.PAINKILLER_DRAWABLE
import com.example.mediaplayer.services.MusicService.Companion.PAINKILLER_RAW_FILE
import com.example.mediaplayer.tools.Tools
import kotlinx.android.synthetic.main.activity_details.*

/**
 * DetailSection shows the name, image and lyrics of the song
 */
class DetailSection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val tools = Tools()
        val infoSong = findViewById<TextView>(R.id.info)
        val songImage = findViewById<ImageView>(R.id.song_image)
        val bundleReceived = intent.extras
        val id = bundleReceived!!.getInt(VARIABLE_NAME_KEY)
        var placeHolder = ""
        var headerHolder: String
        headerHolder = tools.songName(resources.getResourceEntryName(id))
        if (id == CHOP_SUEY_RAW_FILE) {
            headerHolder = "${headerHolder}\nAuthor: System of a Down"
            placeHolder = CHOP_SUEY_LYRICS
            songImage.setImageResource(CHOP_SUEY_DRAWABLE)
        }
        if (id == COCAINE_RAW_FILE) {
            headerHolder = "${headerHolder}\nAuthor: Eric Clapton"
            placeHolder = COCAINE_LYRICS
            songImage.setImageResource(COCAINE_DRAWABLE)
        }
        if (id == PAINKILLER_RAW_FILE) {
            headerHolder = "${headerHolder}\nAuthor: Judas Priest"
            placeHolder = PAINKILLER_LYRICS
            songImage.setImageResource(PAINKILLER_DRAWABLE)
        }
        song_name.text = headerHolder
        infoSong.text = placeHolder
    }

    companion object {
        const val CHOP_SUEY_LYRICS =
            "Lyrics:\nWake up\n Grab a brush and put a little (makeup)\n Grab a brush and put a little\n Hide the scars to fade away the (shakeup)\n Hide the scars to fade away the\nWhy'd you leave the keys upon the table?\nHere you go create another fable\nYou wanted to\nGrab a brush and put a little makeup\nYou wanted to\nHide the scars to fade away the shakeup\nYou wanted to\nWhy'd you leave the keys upon the table?\nYou wanted to"
        const val COCAINE_LYRICS =
            "Lyrics:\nIf you want to hang out, you've gotta take her out, cocaine\nIf you want to get down, get down on the ground, cocaine\nShe don't lie, she don't lie, she don't lie,\nCocaine\nIf you got that lose, you want to kick them blues, cocaine\nWhen your day is done, and you want to ride on cocaine\nShe don't lie, she don't lie, she don't lie,\nCocaine\nIf your day is gone, and you want to ride on, cocaine\nDon't forget this fact, you can't get it back, cocaine\nShe don't lie, she don't lie, she don't lie,\nCocaine\nShe don't lie, she don't lie, she don't lie,\nCocaine\n"
        const val PAINKILLER_LYRICS =
            "Lyrics:\nFaster than a bullet\nTerrifying scream\nEnraged and full of anger\nHe is half man and half machine\nRides the metal monster\nBreathing smoke and fire\nClosing in with vengeance soaring high\nHe is the painkiller\nThis is the painkiller\nPlanets devastated\nMankind's on its knees\nA savior comes from out the skies\nIn answer to their pleas\nThrough boiling clouds if thunder\nBlasting bolts of steel\nEvils going under deadly wheels\nHe is the painkiller\nThis is the painkiller\nFaster then a laser bullet\nLouder than an atom bomb\nChromium plated boiling metal\nBrighter than a thousand suns\nFlying high on rapture\nStronger free and brave\nNevermore encaptured\nThey've been brought back from the grave\nWith mankind resurrected\nForever to survive\nReturns from Armageddon to the skies\nHe is the painkiller\nThis is the painkiller\nWings of steel painkiller\nDeadly wheels painkiller\nHe is the painkiller\nThis is the painkiller\nHe is the painkiller\nThis is the painkiller\nPain! Pain! Killer! Killer!\nPain! Pain! Killer! Killer!\nCan't stop the painkiller\nPain (pain)"

    }
}