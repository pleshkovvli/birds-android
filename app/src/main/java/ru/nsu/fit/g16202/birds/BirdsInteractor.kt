package ru.nsu.fit.g16202.birds

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.bumptech.glide.RequestManager
import ru.nsu.fit.g16202.birds.model.Bird

class BirdsInteractor(
    private val getSoundPlayer: () -> MediaPlayer?,
    private val getImageLoader: () -> RequestManager
) {
    private val birds: List<Bird> = BirdsRepository.birds

    val size: Int = birds.size

    fun createBirdInteractor(position: Int) : BirdInteractor {
        return BirdElementInteractor(
            birds[position],
            { uri ->
                if(getSoundPlayer()?.isPlaying == true) {
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
            },
            {
                if(getSoundPlayer()?.isPlaying == true) {
                    getSoundPlayer()?.stop()
                }
            },
            { uri -> getImageLoader().load(uri) }
        )
    }


}