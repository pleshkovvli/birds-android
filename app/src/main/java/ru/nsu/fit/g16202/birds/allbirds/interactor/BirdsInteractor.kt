package ru.nsu.fit.g16202.birds.allbirds.interactor

import ru.nsu.fit.g16202.birds.bird.interactor.BirdInteractor

interface BirdsInteractor {
    val size: Int

    fun createBirdInteractor(position: Int): BirdInteractor
}