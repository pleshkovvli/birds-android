package ru.nsu.fit.g16202.birds.allbirds

import ru.nsu.fit.g16202.birds.bird.BirdPresenter

class BirdsPresenter(
    private val interactor: BirdsInteractor,
    private val view: BirdsView
) {
    private val birdsPresenters: MutableList<BirdPresenter> = mutableListOf()

    init {
        view.setOnBindBirdViewListener { birdView, pos ->
            val birdInteractor = interactor.createBirdInteractor(pos)
            birdsPresenters.add(
                BirdPresenter(
                    birdInteractor,
                    birdView,
                    interactor.getOnPlayAction()
                )
            )
        }

        view.itemCount = { interactor.size }
    }
}