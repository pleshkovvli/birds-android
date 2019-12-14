package ru.nsu.fit.g16202.birds.bird.interactor

import org.junit.Assert.*
import org.junit.Test
import ru.nsu.fit.g16202.birds.MockedSoundHandler
import ru.nsu.fit.g16202.birds.allbirds.repository.MockedBirdsRepository
import ru.nsu.fit.g16202.birds.bird.imagehandler.ImageLoader

class BirdElementInteractorTest {
    @Test
    fun playSound() {
        val repository = MockedBirdsRepository
        val soundHandler = MockedSoundHandler()

        val bird = repository.birds[1]
        val interactor = BirdElementInteractor(
            { bird },
            { soundHandler.play() },
            { soundHandler.stop() },
            { soundHandler.load(bird.soundUri) }
        )

        assertEquals(bird, interactor.bird)

        var played = false
        var loaded = false
        var stopped = false

        interactor.setOnPlayListener { played = true }
        interactor.setOnSoundLoadedListener { loaded = true }
        interactor.setOnStopListener { stopped = true }

        assertFalse(played)
        assertFalse(loaded)
        assertFalse(stopped)

        interactor.playSound()
        Thread.sleep(500)
        assertTrue(soundHandler.isPlaying())
        assertTrue(played)
        assertFalse(loaded)
        assertTrue(played)

        interactor.stopSound()
        assertTrue(!soundHandler.isPlaying())
        assertTrue(stopped)
    }

    @Test
    fun loadImage() {
        val repository = MockedBirdsRepository
        val soundHandler = MockedSoundHandler()

        val bird = repository.birds[1]
        val interactor = BirdElementInteractor(
            { bird },
            { soundHandler.play() },
            { soundHandler.stop() },
            { soundHandler.load(bird.soundUri) }
        )

        val loader = object : ImageLoader {
            var loaded = false

            override fun loadImage(uri: String) {
                loaded = true
            }
        }
        interactor.imageLoader = loader

        assertFalse(loader.loaded)
        interactor.loadImage()
        assertTrue(loader.loaded)
    }
}