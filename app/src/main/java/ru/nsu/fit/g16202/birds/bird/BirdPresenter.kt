package ru.nsu.fit.g16202.birds.bird

class BirdPresenter(
    private val interactor: BirdInteractor,
    private val view: BirdView
) {
    init {
        view.fillView = {
            view.description = interactor.bird.description
            view.name = interactor.bird.speciesName
            view.setOnPlaySoundListener { interactor.playSound() }
            view.setOnStopSoundListener { interactor.stopSound() }
            view.showImage(interactor.loadImage())
        }
    }
}