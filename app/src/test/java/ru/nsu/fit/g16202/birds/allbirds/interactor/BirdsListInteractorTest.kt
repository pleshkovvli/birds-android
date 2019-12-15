package ru.nsu.fit.g16202.birds.allbirds.interactor

import org.junit.Assert
import org.junit.Test
import ru.nsu.fit.g16202.birds.MockedSoundHandler

import ru.nsu.fit.g16202.birds.allbirds.repository.MockedBirdsRepository

class BirdsListInteractorTest {

    @Test
    fun getSize() {
        val interactor = BirdsListInteractor(MockedBirdsRepository,
            MockedSoundHandler()
        )
        Assert.assertEquals(MockedBirdsRepository.birds.size, interactor.size)
    }

    @Test
    fun createBirdInteractor() {
        val soundHandler = MockedSoundHandler()
        val birdsRepository = MockedBirdsRepository

        val interactor = BirdsListInteractor(birdsRepository, soundHandler)
        val position = 1

        val birdInteractor = interactor.createBirdInteractor(position)

        Assert.assertEquals(birdInteractor.bird, MockedBirdsRepository.birds[position])

        birdInteractor.playSound()
        Thread.sleep(500)
        Assert.assertTrue(soundHandler.isPlaying())

        birdInteractor.stopSound()
        Assert.assertTrue(!soundHandler.isPlaying())
    }

    @Test
    fun playSeveralSounds() {
        val soundHandler = MockedSoundHandler()
        val birdsRepository = MockedBirdsRepository

        val interactor = BirdsListInteractor(birdsRepository, soundHandler)

        val oneBirdInteractor = interactor.createBirdInteractor(5)
        soundHandler.load(oneBirdInteractor.bird.soundUri)
        soundHandler.play()

        oneBirdInteractor.playSound()
        Thread.sleep(500)
        Assert.assertTrue(soundHandler.isPlaying())


        val positions = listOf(1,2,3,4)

        val birdInteractors = positions.map { pos ->
            interactor.createBirdInteractor(pos)
        }.toMutableList().also { it.add(oneBirdInteractor) }

        birdInteractors.forEach { birdInteractor ->
            birdInteractor.playSound()
            Thread.sleep(500)
            Assert.assertTrue(soundHandler.isPlaying())
        }

        birdInteractors.forEach { birdInteractor ->
            birdInteractor.stopSound()
            Assert.assertTrue(!soundHandler.isPlaying())

            birdInteractor.playSound()
            Thread.sleep(500)
            Assert.assertTrue(soundHandler.isPlaying())

            if(birdInteractor.bird.id == birdInteractors.last().bird.id) {
                birdInteractor.stopSound()
                Assert.assertTrue(!soundHandler.isPlaying())
            }
        }
    }
}

