package ru.nsu.fit.g16202.birds.allbirds.soundhandler

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

class MediaPlayerSoundHandler(
    private val soundLoader: SoundLoader,
    private val context: Context,
    private val getPlayer: () -> MediaPlayer?
) : SoundHandler {
    override fun isPlaying(): Boolean = getPlayer()?.isPlaying ?: false

    override fun load(uri: String) {
        val byteArray = soundLoader.loadSoundData(uri)

        val localUrl = getLocalUri(byteArray)
        getPlayer()?.prepareMediaPlayer(localUrl)
    }


    override fun play() {
        getPlayer()?.start()
    }

    override fun stop() {
        getPlayer()?.stop()
    }

    private fun getLocalUri(byteArray: ByteArray): Uri {
        val file = File.createTempFile("prefix", "suffix")
        val fos = FileOutputStream(file)
        fos.write(byteArray)

        return Uri.fromFile(file)
    }

    private fun MediaPlayer.prepareMediaPlayer(localUrl: Uri) {
        reset()
        setAudioAttributes(
            AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        setDataSource(context, localUrl)
        prepare()
    }
}