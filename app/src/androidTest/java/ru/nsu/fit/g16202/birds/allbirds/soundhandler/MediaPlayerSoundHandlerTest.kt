package ru.nsu.fit.g16202.birds.allbirds.soundhandler

import android.media.MediaPlayer
import org.junit.Test

import org.junit.Assert.*

class MediaPlayerSoundHandlerTest {

    @Test
    fun isPlaying() {
        var player: MediaPlayer? = null
        val soundUri = "https://www.xeno-canto.org/sounds/uploaded/YQNGFTBRRT/XC237160-BRTI_Chiricahuas_3May2013_Harter_1.mp3"

        val handler = MediaPlayerSoundHandler { player }

        assertFalse(handler.isPlaying())

        handler.load(soundUri)
        handler.play()

        assertFalse(handler.isPlaying())

        handler.stop()
        assertFalse(handler.isPlaying())

        player = MediaPlayer()

        assertFalse(player.isPlaying)
        assertEquals(player.isPlaying, handler.isPlaying())

        handler.load(soundUri)
        handler.play()

        assertTrue(player.isPlaying)
        assertEquals(player.isPlaying, handler.isPlaying())

        handler.stop()
        assertFalse(player.isPlaying)
        assertEquals(player.isPlaying, handler.isPlaying())
    }
}