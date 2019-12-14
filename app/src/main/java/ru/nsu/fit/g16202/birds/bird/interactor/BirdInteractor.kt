package ru.nsu.fit.g16202.birds.bird.interactor

import ru.nsu.fit.g16202.birds.bird.imagehandler.ImageLoader
import ru.nsu.fit.g16202.birds.bird.entity.Bird

interface BirdInteractor {
    val bird: Bird

    var imageLoader: ImageLoader

    fun setOnPlayListener(listener: () -> Unit)
    fun setOnStopListener(listener: () -> Unit)
    fun setOnSoundLoadedListener(listener: () -> Unit)

    fun playSound()
    fun stopSound()

    fun loadImage()
}