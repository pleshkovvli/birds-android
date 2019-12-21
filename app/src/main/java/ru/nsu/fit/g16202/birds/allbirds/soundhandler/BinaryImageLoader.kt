package ru.nsu.fit.g16202.birds.allbirds.soundhandler

import android.util.Base64
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class BinaryImageLoader : SoundLoader {
    override fun loadSoundData(uri: String): ByteArray {
        return runBlocking(Dispatchers.IO) {
            val (_, _, result) = uri
                .httpGet()
                .response()

            when (result) {
                is Result.Failure -> {
                    throw result.getException()
                }
                is Result.Success -> {
                    result.value
                }
            }
        }
    }
}