package ru.nsu.fit.g16202.birds.allbirds.interactor

import ru.nsu.fit.g16202.birds.allbirds.repository.BirdsRepository
import ru.nsu.fit.g16202.birds.allbirds.soundhandler.SoundHandler
import ru.nsu.fit.g16202.birds.bird.entity.Bird
import ru.nsu.fit.g16202.birds.bird.interactor.BirdElementInteractor
import ru.nsu.fit.g16202.birds.bird.interactor.BirdInteractor

class BirdsListInteractor(
    private val birdsRepository: BirdsRepository,
    private val soundHandler: SoundHandler
) : BirdsInteractor {
    private val birds: List<Bird> = birdsRepository.birds

    private val interactors: MutableMap<String, BirdInteractor> = mutableMapOf()

    override val size: Int = birds.size

    override fun createBirdInteractor(position: Int): BirdInteractor {
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