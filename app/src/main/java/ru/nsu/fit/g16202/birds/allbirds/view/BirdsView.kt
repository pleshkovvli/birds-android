package ru.nsu.fit.g16202.birds.allbirds.view

import ru.nsu.fit.g16202.birds.bird.view.BirdView

interface BirdsView {
    fun setOnBindBirdViewListener(listener: ((BirdView, Int) -> Unit)?)

    var itemCount: () -> Int
}