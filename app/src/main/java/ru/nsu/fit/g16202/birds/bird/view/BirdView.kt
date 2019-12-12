package ru.nsu.fit.g16202.birds.bird.view

import ru.nsu.fit.g16202.birds.bird.ImageShow

interface BirdView {
    var fillView: (() -> Unit)?

    var name: String
    var description: String
    fun setOnPlaySoundListener(listener: (() -> Unit)?)
    fun setOnStopSoundListener(listener: (() -> Unit)?)

    fun loadSound()

    fun playSound()
    fun stopSound()

    var imageShow: ImageShow
    fun showImage()
}