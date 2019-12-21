package ru.nsu.fit.g16202.birds.allbirds.soundhandler

import android.util.Base64
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class Base64DataLoader : SoundLoader {
    override fun loadSoundData(uri: String): ByteArray {
        return runBlocking(Dispatchers.IO) {
            val (_, _, result) = uri
                .httpGet()
                .responseString()

            when (result) {
                is com.github.kittinunf.result.Result.Failure -> {
                    throw result.getException()
                }
                is com.github.kittinunf.result.Result.Success -> {
                    Base64.decode(result.value, Base64.DEFAULT)
                }
            }
        }
    }
}