package ru.nsu.fit.g16202.birds.bird.interactor

import kotlinx.coroutines.*
import ru.nsu.fit.g16202.birds.bird.ImageLoader
import ru.nsu.fit.g16202.birds.bird.entity.Bird

class BirdElementInteractor(
    override val bird: Bird,
    private val play: (String) -> Unit,
    private val stop: () -> Unit
) : BirdInteractor {

    private var onPlayListener: ((BirdInteractor) -> Unit)? = null
    private var onStopListener: ((BirdInteractor) -> Unit)? = null
    private var onSoundLoadedListener: ((BirdInteractor) -> Unit)? = null

    override lateinit var imageLoader: ImageLoader

    override fun playSound() {
        onPlayListener?.invoke(this)
        CoroutineScope(Dispatchers.IO).launch {
            play(bird.soundUri)
            withContext(Dispatchers.Main) {
                onSoundLoadedListener?.invoke(this@BirdElementInteractor)
            }
        }
    }

    override fun stopSound() {
        onStopListener?.invoke(this)
        stop()
    }

    override fun loadImage() {
        imageLoader.loadImage(bird.imageUri)
    }

    override fun setOnPlayListener(listener: (BirdInteractor) -> Unit) {
        onPlayListener = listener
    }

    override fun setOnStopListener(listener: (BirdInteractor) -> Unit) {
        onStopListener = listener
    }

    override fun setOnSoundLoadedListener(listener: (BirdInteractor) -> Unit) {
        onSoundLoadedListener = listener
    }
}