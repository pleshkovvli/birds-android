package ru.nsu.fit.g16202.birds.bird

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder

interface BirdView {
    var fillView: (() -> Unit)?

    var name: String
    var description: String
    fun setOnPlaySoundListener(listener: (() -> Unit)?)
    fun setOnStopSoundListener(listener: (() -> Unit)?)

    fun loadSound()

    fun playSound()
    fun stopSound()

    fun showImage(imageHandler: RequestBuilder<Drawable>)
}