package ru.nsu.fit.g16202.birds.bird.interactor

import kotlinx.coroutines.*
import ru.nsu.fit.g16202.birds.bird.imagehandler.ImageLoader
import ru.nsu.fit.g16202.birds.bird.entity.Bird

class BirdElementInteractor(
    private val getBird: () -> Bird,
    private val play: () -> Unit,
    private val stop: () -> Unit,
    private val preparePlay: () -> Unit
) : BirdInteractor {

    override val bird: Bird
        get() = getBird()

    private var onPlayListener: (() -> Unit)? = null
    private var onStopListener: (() -> Unit)? = null
    private var onSoundLoadedListener: (() -> Unit)? = null

    override lateinit var imageLoader: ImageLoader

    override fun playSound() {
        onPlayListener?.invoke()
        preparePlay()
        CoroutineScope(Dispatchers.IO).launch {
            play()
            withContext(Dispatchers.Main) {
                onSoundLoadedListener?.invoke()
            }
        }
    }

    override fun stopSound() {
        onStopListener?.invoke()
        stop()
    }

    override fun loadImage() {
        imageLoader.loadImage(bird.imageUri)
    }

    override fun setOnPlayListener(listener: () -> Unit) {
        onPlayListener = listener
    }

    override fun setOnStopListener(listener: () -> Unit) {
        onStopListener = listener
    }

    override fun setOnSoundLoadedListener(listener: () -> Unit) {
        onSoundLoadedListener = listener
    }
}