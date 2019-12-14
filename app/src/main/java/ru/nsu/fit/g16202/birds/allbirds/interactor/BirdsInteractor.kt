package ru.nsu.fit.g16202.birds.allbirds.interactor

import ru.nsu.fit.g16202.birds.allbirds.soundhandler.SoundHandler
import ru.nsu.fit.g16202.birds.allbirds.repository.BirdsRepository
import ru.nsu.fit.g16202.birds.bird.entity.Bird
import ru.nsu.fit.g16202.birds.bird.interactor.BirdElementInteractor
import ru.nsu.fit.g16202.birds.bird.interactor.BirdInteractor

class BirdsInteractor(private val soundHandler: SoundHandler) {
    private val birds: List<Bird> = BirdsRepository.birds

    private val interactors: MutableMap<String, BirdInteractor> = mutableMapOf()

    val size: Int = birds.size

    fun createBirdInteractor(position: Int): BirdInteractor {
        return BirdElementInteractor(
            { birds[position] },
            { playBirdSound(birds[position].soundUri) },
            { stopBirdSound() },
            {
                interactors.forEach { currentInteractor ->
                    if (currentInteractor.value.bird.id != birds[position].id) {
                        currentInteractor.value.stopSound()
                    }
                }
            }
        ).also { interactors[birds[position].id] = it }
    }

    private fun stopBirdSound() {
        if (soundHandler.isPlaying()) {
            soundHandler.stop()
        }
    }

    private val lock = Object()

    private fun playBirdSound(uri: String) = synchronized(lock)
    {
        if (soundHandler.isPlaying()) {
            soundHandler.stop()
        }

        soundHandler.load(uri)
        soundHandler.play()
    }
}