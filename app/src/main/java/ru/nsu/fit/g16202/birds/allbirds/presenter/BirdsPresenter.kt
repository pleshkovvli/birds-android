package ru.nsu.fit.g16202.birds.allbirds.presenter

import ru.nsu.fit.g16202.birds.allbirds.view.BirdsView
import ru.nsu.fit.g16202.birds.allbirds.interactor.BirdsInteractor
import ru.nsu.fit.g16202.birds.bird.ImageHandler
import ru.nsu.fit.g16202.birds.bird.presenter.BirdPresenter

class BirdsPresenter(
    private val interactor: BirdsInteractor,
    private val view: BirdsView,
    private val getImageShow: () -> ImageHandler
) {
    private val birdsPresenters: MutableList<BirdPresenter> = mutableListOf()

    init {
        view.setOnBindBirdViewListener { birdView, pos ->
            val birdInteractor = interactor.createBirdInteractor(pos)
            birdsPresenters.add(
                BirdPresenter(
                    birdInteractor,
                    birdView,
                    interactor.getOnPlayAction(),
                    getImageShow
                )
            )
        }

        view.itemCount = { interactor.size }
    }
}