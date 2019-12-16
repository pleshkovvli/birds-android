package ru.nsu.fit.g16202.birds.bird.presenter

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import ru.nsu.fit.g16202.birds.allbirds.repository.MockedBirdsRepository
import ru.nsu.fit.g16202.birds.bird.entity.Bird
import ru.nsu.fit.g16202.birds.bird.imagehandler.ImageHandler
import ru.nsu.fit.g16202.birds.bird.imagehandler.ImageLoader
import ru.nsu.fit.g16202.birds.bird.interactor.BirdInteractor
import ru.nsu.fit.g16202.birds.bird.view.BirdView

class BirdElementPresenterTest {

    @Test
    fun bindViewWithInteractor() {
        val oneBird = MockedBirdsRepository.birds[2]

        val interactor = object : BirdInteractor {
            var imageLoaderSet = false
            var imageLoaded = false

            var onPlayListenerSet = false
            var onStopListenerSet = false
            var onSoundLoaderListenerSet = false

            var played = false
            var stopped = false
            var loaded = false

            override val bird: Bird
                get() = oneBird

            override var imageLoader: ImageLoader = object : ImageLoader {
                    override fun loadImage(uri: String) {
                        throw NotImplementedError()
                    }
                }
                set(value) {
                    field = value
                    imageLoaderSet = true
                }

            override fun setOnPlayListener(listener: () -> Unit) {
                onPlayListenerSet = true
            }

            override fun setOnStopListener(listener: () -> Unit) {
                onStopListenerSet = true
            }

            override fun setOnSoundLoadedListener(listener: suspend () -> Unit) {
                onSoundLoaderListenerSet = true
            }

            override fun playSound() {
                played = true
            }

            override fun stopSound() {
                stopped = true
            }

            override fun loadImage() {
                loaded = true
                imageLoader.loadImage("")
            }
        }

        val view = object : BirdView {
            var onPlayListenerSet = false
            var onStopListenerSet = false
            var onLoadImageListenerSet = false

            var imageShow = false

            var played = false
            var stopped = false
            var loaded = false

            override var fillView: (() -> Unit)? = null
            override var name: String = ""
            override var description: String = ""

            override fun getImageHandler(): ImageHandler = object : ImageHandler {
                override fun loadImage(uri: String) {
                    interactor.imageLoaded = true
                }

                override fun showImage() {
                    throw NotImplementedError()
                }
            }

            override fun setOnLoadImageListener(listener: (() -> Unit)?) {
                onLoadImageListenerSet = true
            }

            override fun setOnPlaySoundListener(listener: (() -> Unit)?) {
                onPlayListenerSet = true
            }

            override fun setOnStopSoundListener(listener: (() -> Unit)?) {
                onStopListenerSet = true
            }

            override suspend fun playSound() {
                played = true
            }

            override fun stopSound() {
                stopped = true
            }

            override fun loadSound() {
                loaded = true
            }
            override fun showImage() {
                imageShow = true
            }
        }

        val presenter = BirdElementPresenter(interactor, view)

        assertFalse(interactor.imageLoaderSet)
        assertFalse(interactor.imageLoaded)
        assertFalse(interactor.onPlayListenerSet)
        assertFalse(interactor.onStopListenerSet)
        assertFalse(interactor.onSoundLoaderListenerSet)
        assertFalse(interactor.played)
        assertFalse(interactor.stopped)
        assertFalse(interactor.loaded)

        assertFalse(view.onLoadImageListenerSet)
        assertFalse(view.onPlayListenerSet)
        assertFalse(view.onStopListenerSet)
        assertFalse(view.imageShow)
        assertFalse(view.played)
        assertFalse(view.stopped)
        assertFalse(view.loaded)

        presenter.bindViewWithInteractor()
        view.fillView!!()


        assertEquals(oneBird.description, view.description)
        assertEquals(oneBird.name, view.name)

        assertTrue(view.onLoadImageListenerSet)
        assertTrue(interactor.imageLoaderSet)
        assertTrue(interactor.imageLoaded)
        assertTrue(interactor.onPlayListenerSet)
        assertTrue(interactor.onStopListenerSet)
        assertTrue(interactor.onSoundLoaderListenerSet)

        interactor.playSound()
        assertTrue(interactor.played)

        interactor.stopSound()
        assertTrue(interactor.stopped)

        interactor.loadImage()
        assertTrue(interactor.loaded)

        assertTrue(view.onPlayListenerSet)
        assertTrue(view.onStopListenerSet)
        assertTrue(view.imageShow)

        runBlocking { view.playSound() }
        assertTrue(view.played)

        view.stopSound()
        assertTrue(view.stopped)

        view.loadSound()
        assertTrue(view.loaded)
    }
}