package ru.nsu.fit.g16202.birds.allbirds.soundhandler

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Base64
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import java.io.File
import java.io.FileOutputStream

class MediaPlayerSoundHandler(
    private val context: Context,
    private val getPlayer: () -> MediaPlayer?
) : SoundHandler {
    override fun isPlaying(): Boolean = getPlayer()?.isPlaying ?: false

    override fun load(uri: String) {
        val (_, _, result) = uri
            .httpGet()
            .responseString()

        val byteArray = when (result) {
            is com.github.kittinunf.result.Result.Failure -> {
                throw result.getException()
            }
            is com.github.kittinunf.result.Result.Success -> {
                val data: String = result.get()
                val stringData = Klaxon().parse<AudioHolder>(data)!!.data
                Base64.decode(stringData, Base64.DEFAULT)
            }
        }

        val file = File.createTempFile("prefix","suffix")
        val fos = FileOutputStream(file)
        fos.write(byteArray)

        val localUrl = Uri.fromFile(file)

        getPlayer()?.apply {
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


    override fun play() {
        getPlayer()?.start()
    }

    override fun stop() {
        getPlayer()?.stop()
    }

    private class AudioHolder(val data: String)
}