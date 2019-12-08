package ru.nsu.fit.g16202.birds

class BirdsPresenter(
    private val interactor: BirdsInteractor,
    private val view: BirdsView
) {
    private val birdsPresenters: MutableList<BirdPresenter> = mutableListOf()

    init {
        view.setOnBindBirdViewListener { birdView, pos ->
            val birdInteractor = interactor.createBirdInteractor(pos)
            birdsPresenters.add(BirdPresenter(birdInteractor, birdView))
        }

        view.itemCount = { interactor.size }
    }
}