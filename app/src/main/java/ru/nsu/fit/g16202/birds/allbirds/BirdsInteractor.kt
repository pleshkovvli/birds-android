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

    val size: Int = birds.size

    fun createBirdInteractor(position: Int) : BirdInteractor {
        return BirdElementInteractor(
            birds[position],
            { uri -> playBirdSound(uri) },
            { stopBirdSound() },
            { uri -> getImageLoader().load(uri) }
        )
    }

    private fun stopBirdSound() {
        if (getSoundPlayer()?.isPlaying == true) {
            getSoundPlayer()?.stop()
        }
    }

    private fun playBirdSound(uri: String) {
        if (getSoundPlayer()?.isPlaying == true) {
            getSoundPlayer()?.stop()
        }

        getSoundPlayer()?.apply {
            getSoundPlayer()?.reset()
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