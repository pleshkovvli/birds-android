package ru.nsu.fit.g16202.birds.bird.presenter

import ru.nsu.fit.g16202.birds.bird.interactor.BirdInteractor
import ru.nsu.fit.g16202.birds.bird.view.BirdView

class BirdElementPresenter(
    private val interactor: BirdInteractor,
    private val view: BirdView
) : BirdPresenter {

    override fun bindViewWithInteractor() {
        view.fillView = {
            view.description = interactor.bird.description
            view.name = interactor.bird.speciesName
            view.setOnPlaySoundListener { interactor.playSound() }
            view.setOnStopSoundListener { interactor.stopSound() }

            val imageHandler = view.getImageHandler()
            interactor.imageLoader = imageHandler

            interactor.loadImage()
            view.showImage()

            interactor.setOnPlayListener { view.loadSound() }
            interactor.setOnStopListener { view.stopSound() }
            interactor.setOnSoundLoadedListener { view.playSound() }
        }
    }
}