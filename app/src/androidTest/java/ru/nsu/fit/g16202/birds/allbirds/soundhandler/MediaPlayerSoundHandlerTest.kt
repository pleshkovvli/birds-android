package ru.nsu.fit.g16202.birds.allbirds.soundhandler

import android.content.Context
import android.media.MediaPlayer
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class MediaPlayerSoundHandlerTest {

    private lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun isPlaying() {
        var player: MediaPlayer? = null
        val soundUri = "https://www.xeno-canto.org/sounds/uploaded/YQNGFTBRRT/XC237160-BRTI_Chiricahuas_3May2013_Harter_1.mp3"

        val handler = MediaPlayerSoundHandler(instrumentationContext) { player }

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