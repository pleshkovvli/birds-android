package ru.nsu.fit.g16202.birds

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import ru.nsu.fit.g16202.birds.model.Bird

interface BirdInteractor {
    val bird: Bird

    fun playSound()
    fun stopSound()

    fun loadImage() : RequestBuilder<Drawable>
}