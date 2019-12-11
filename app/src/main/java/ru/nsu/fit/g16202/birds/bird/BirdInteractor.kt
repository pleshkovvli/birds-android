package ru.nsu.fit.g16202.birds.bird

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder

interface BirdInteractor {
    val bird: Bird

    fun setOnPlayListener(listener: (BirdInteractor) -> Unit)
    fun setOnStopListener(listener: (BirdInteractor) -> Unit)
    fun setOnSoundLoadedListener(listener: (BirdInteractor) -> Unit)

    fun playSound()
    fun stopSound()

    fun loadImage() : RequestBuilder<Drawable>
}