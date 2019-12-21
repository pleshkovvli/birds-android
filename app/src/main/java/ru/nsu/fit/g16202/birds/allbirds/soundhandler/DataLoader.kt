package ru.nsu.fit.g16202.birds.allbirds.soundhandler

interface DataLoader {
    fun loadData(uri: String) : ByteArray
}