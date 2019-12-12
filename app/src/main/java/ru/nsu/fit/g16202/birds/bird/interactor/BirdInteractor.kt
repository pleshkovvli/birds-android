package ru.nsu.fit.g16202.birds.bird.interactor

import ru.nsu.fit.g16202.birds.bird.ImageLoader
import ru.nsu.fit.g16202.birds.bird.entity.Bird

interface BirdInteractor {
    val bird: Bird

    var imageLoader: ImageLoader

    fun setOnPlayListener(listener: (BirdInteractor) -> Unit)
    fun setOnStopListener(listener: (BirdInteractor) -> Unit)
    fun setOnSoundLoadedListener(listener: (BirdInteractor) -> Unit)

    fun playSound()
    fun stopSound()

    fun loadImage()
}