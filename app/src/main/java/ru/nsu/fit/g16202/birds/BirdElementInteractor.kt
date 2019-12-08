package ru.nsu.fit.g16202.birds

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import ru.nsu.fit.g16202.birds.model.Bird

class BirdElementInteractor(
    override val bird: Bird,
    private val play: (String) -> Unit,
    private val stop: () -> Unit,
    private val load: (String) -> RequestBuilder<Drawable>
) : BirdInteractor {

    override fun playSound() {
        play(bird.soundUri)
    }

    override fun stopSound() {
        stop()
    }

    override fun loadImage(): RequestBuilder<Drawable> {
        return load(bird.imageUri)
    }
}