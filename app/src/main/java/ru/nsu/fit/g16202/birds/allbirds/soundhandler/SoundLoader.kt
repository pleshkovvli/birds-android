package ru.nsu.fit.g16202.birds.allbirds.soundhandler

interface SoundLoader {
    fun loadSoundData(uri: String) : ByteArray
}