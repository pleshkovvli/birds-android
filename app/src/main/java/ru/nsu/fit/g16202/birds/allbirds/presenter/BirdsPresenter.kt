package ru.nsu.fit.g16202.birds.allbirds.presenter

import ru.nsu.fit.g16202.birds.allbirds.interactor.BirdsInteractor
import ru.nsu.fit.g16202.birds.allbirds.view.BirdsView
import ru.nsu.fit.g16202.birds.bird.view.BirdView

class BirdsPresenter(
    private val interactor: BirdsInteractor,
    private val view: BirdsView,
    private val birdViewBinder: (BirdView, Int) -> Unit
) {
    init {
        view.setOnBindBirdViewListener { birdView, pos ->
            birdViewBinder(birdView, pos)
        }

        view.itemCount = { interactor.size }
    }
}