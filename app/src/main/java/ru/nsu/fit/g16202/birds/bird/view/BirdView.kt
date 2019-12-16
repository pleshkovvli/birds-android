package ru.nsu.fit.g16202.birds.bird.view

import ru.nsu.fit.g16202.birds.bird.imagehandler.ImageHandler

interface BirdView {
    var fillView: (() -> Unit)?

    var name: String
    var description: String

    fun getImageHandler() : ImageHandler

    fun setOnPlaySoundListener(listener: (() -> Unit)?)
    fun setOnStopSoundListener(listener: (() -> Unit)?)
    fun setOnLoadImageListener(listener: (() -> Unit)?)

    fun loadSound()

    suspend fun playSound()
    fun stopSound()

    fun showImage()
}