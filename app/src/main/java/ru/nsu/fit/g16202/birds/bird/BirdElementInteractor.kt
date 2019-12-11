package ru.nsu.fit.g16202.birds.bird

import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.RequestBuilder
import kotlinx.coroutines.*

class BirdElementInteractor(
    override val bird: Bird,
    private val play: (String) -> Unit,
    private val stop: () -> Unit,
    private val load: (String) -> RequestBuilder<Drawable>
) : BirdInteractor {

    private var deferred: Deferred<*>? = null

    private var onPlayListener: ((BirdInteractor) -> Unit)? = null
    private var onStopListener: ((BirdInteractor) -> Unit)? = null
    private var onSoundLoadedListener: ((BirdInteractor) -> Unit)? = null

    override fun playSound() {
        onPlayListener?.invoke(this)
        deferred = CoroutineScope(Dispatchers.IO).async(Dispatchers.IO) {
            play(bird.soundUri)
            withContext(Dispatchers.Main) {
                onSoundLoadedListener?.invoke(this@BirdElementInteractor)
            }
        }
    }

    override fun cancelPlaying() {

    }

    override fun stopSound() {
        onStopListener?.invoke(this)
        stop()
    }

    override fun loadImage(): RequestBuilder<Drawable> {
        return load(bird.imageUri)
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