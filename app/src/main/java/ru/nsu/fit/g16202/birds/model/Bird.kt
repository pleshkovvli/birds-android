package ru.nsu.fit.g16202.birds.model

data class Bird(val speciesName: String, val description: String, val imageUri: String) {
    override fun toString(): String = "$speciesName. $description"
}