package ru.nsu.fit.g16202.birds.bird.entity

data class Bird(
    val id: String,
    val speciesName: String,
    val description: String,
    val imageUri: String,
    val soundUri: String
) {
    override fun toString(): String = "$speciesName. $description"
}