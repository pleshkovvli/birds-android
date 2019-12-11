package ru.nsu.fit.g16202.birds.allbirds

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.bumptech.glide.RequestManager
import ru.nsu.fit.g16202.birds.bird.Bird
import ru.nsu.fit.g16202.birds.bird.BirdElementInteractor
import ru.nsu.fit.g16202.birds.bird.BirdInteractor

class BirdsInteractor(
    private val getSoundPlayer: () -> MediaPlayer?,
    private val getImageLoader: () -> RequestManager
) {
    private val birds: List<Bird> = BirdsRepository.birds

    private val interactors: MutableList<BirdInteractor> = mutableListOf()

    val size: Int = birds.size

    fun createBirdInteractor(position: Int): BirdInteractor {
        return BirdElementInteractor(
            birds[position],
            { uri -> playBirdSound(uri) },
            { stopBirdSound() },
            { uri -> getImageLoader().load(uri) }
        ).also { interactors.add(it) }
    }

    fun getOnPlayAction(): (BirdInteractor) -> Unit {
        return { birdInteractor ->
            interactors.forEach { currentInteractor ->
                if (currentInteractor !== birdInteractor) {
                    currentInteractor.stopSound()
                }
            }
        }
    }

    private fun stopBirdSound() {
        if (getSoundPlayer()?.isPlaying == true) {
            getSoundPlayer()?.stop()
        }
    }

    private val lock = Object()

    private fun playBirdSound(uri: String) = synchronized(lock)
    {
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

    }
}