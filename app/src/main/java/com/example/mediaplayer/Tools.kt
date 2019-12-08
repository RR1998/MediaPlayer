package com.example.mediaplayer

class Tools {
    fun songName(song: String): String {
        val songSpited = song.split("_")
        var newNameSong = ""
        songSpited.forEach {
            newNameSong = "$newNameSong $it"
        }
        return newNameSong
    }

}