package ru.nsu.fit.g16202.birds.allbirds

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import com.bumptech.glide.RequestManager
import ru.nsu.fit.g16202.birds.bird.Bird
import ru.nsu.fit.g16202.birds.bird.BirdElementInteractor
import ru.nsu.fit.g16202.birds.bird.BirdInteractor
import java.lang.Exception

class BirdsInteractor(
    private val getSoundPlayer: () -> MediaPlayer?,
    private val getImageLoader: () -> RequestManager
) {
    private val birds: List<Bird> = BirdsRepository.birds

    private val interactors: MutableList<BirdInteractor> = mutableListOf()

    val size: Int = birds.size

    fun createBirdInteractor(position: Int) : BirdInteractor {
        return BirdElementInteractor(
            birds[position],
            { uri -> playBirdSound(uri) },
            {
                stopBirdSound()
            },
            { uri -> getImageLoader().load(uri) }
        ).also {
            interactors.add(it)
        }
    }

    fun getOnPlayAction() : (BirdInteractor) -> Unit {
        return { birdInteractor ->
            interactors.forEach { currentInteractor ->
                currentInteractor.cancelPlaying()
                if(currentInteractor !== birdInteractor) {
                    currentInteractor.stopSound()
                }
            }
        }
    }

    private val lock = java.lang.Object()

    private fun stopBirdSound() //= synchronized(lock)
    {
        if (getSoundPlayer()?.isPlaying == true) {
            getSoundPlayer()?.stop()
        }
    }

    private fun playBirdSound(uri: String) = synchronized(lock)
    {
        try {
            if (getSoundPlayer()?.isPlaying == true) {
                getSoundPlayer()?.stop()
            }

            getSoundPlayer()?.apply {
                reset()
                setAudioAttributes(
                    AudioAttributes
                        .Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setDataSource(uri)
                prepare()
                start()
            }
        } catch (e: Throwable) {
            Log.d("playBirdSound", "Exception: ${e.message}")
        }
    }
}