package ru.nsu.fit.g16202.birds.bird

class BirdPresenter(
    private val interactor: BirdInteractor,
    private val view: BirdView,
    onPlayAction: (BirdInteractor) -> Unit
) {
    init {
        view.fillView = {
            view.description = interactor.bird.description
            view.name = interactor.bird.speciesName
            view.setOnPlaySoundListener { interactor.playSound() }
            view.setOnStopSoundListener { interactor.stopSound() }
            view.showImage(interactor.loadImage())

            interactor.setOnPlayListener {
                view.loadSound()
                onPlayAction(it)
            }
            interactor.setOnStopListener { view.stopSound() }
            interactor.setOnSoundLoadedListener { view.playSound() }
        }
    }
}