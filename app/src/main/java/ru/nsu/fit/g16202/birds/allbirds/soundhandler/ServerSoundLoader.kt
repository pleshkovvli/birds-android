package ru.nsu.fit.g16202.birds.allbirds.soundhandler

import android.util.Base64
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet

class ServerSoundLoader : SoundLoader {
    override fun loadSoundData(uri: String): ByteArray {
        val (_, _, result) = uri
            .httpGet()
            .responseString()

        return when (result) {
            is com.github.kittinunf.result.Result.Failure -> {
                throw result.getException()
            }
            is com.github.kittinunf.result.Result.Success -> {
                val data: String = result.get()
                val stringData = Klaxon().parse<AudioHolder>(data)!!.data

                Base64.decode(stringData, Base64.DEFAULT)
            }
        }
    }

    private class AudioHolder(val data: String)
}