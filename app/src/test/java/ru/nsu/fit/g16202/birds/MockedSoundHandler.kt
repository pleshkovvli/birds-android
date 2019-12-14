package ru.nsu.fit.g16202.birds

import ru.nsu.fit.g16202.birds.allbirds.soundhandler.SoundHandler

class MockedSoundHandler : SoundHandler {
    private var isPlaying: Boolean = false
    private var isLoading: Boolean = false

    override fun isPlaying(): Boolean = isPlaying

    override fun load(uri: String) {
        isLoading = true
    }

    override fun play() {
        if(isLoading) {
            isPlaying = true
            isLoading = false
        }
    }

    override fun stop() {
        isLoading = false
        isPlaying = false
    }
}