package ru.nsu.fit.g16202.birds.allbirds.soundhandler

import android.media.AudioAttributes
import android.media.MediaPlayer

class MediaPlayerSoundHandler(private val getPlayer: () -> MediaPlayer?) : SoundHandler {
    override fun isPlaying(): Boolean = getPlayer()?.isPlaying ?: false

    override fun load(uri: String) {
        getPlayer()?.apply {
            reset()
            setAudioAttributes(
                AudioAttributes
                    .Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setDataSource(uri)
            prepare()
        }
    }

    override fun play() {
        getPlayer()?.start()
    }

    override fun stop() {
        getPlayer()?.stop()
    }
}