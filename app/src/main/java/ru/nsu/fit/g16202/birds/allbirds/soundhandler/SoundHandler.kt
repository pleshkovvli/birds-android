package ru.nsu.fit.g16202.birds.allbirds.soundhandler

interface SoundHandler {
    fun isPlaying() : Boolean

    fun load(uri: String)
    fun play()
    fun stop()

}