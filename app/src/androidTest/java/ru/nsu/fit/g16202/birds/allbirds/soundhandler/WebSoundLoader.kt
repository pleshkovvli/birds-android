package ru.nsu.fit.g16202.birds.allbirds.soundhandler

import com.github.kittinunf.fuel.httpGet

class WebSoundLoader: SoundLoader {
    override fun loadSoundData(uri: String): ByteArray {
        val (_, _, result) = uri
            .httpGet()
            .response()

        return when (result) {
            is com.github.kittinunf.result.Result.Failure -> {
                throw result.getException()
            }
            is com.github.kittinunf.result.Result.Success -> {
                result.get()
            }
        }
    }
}