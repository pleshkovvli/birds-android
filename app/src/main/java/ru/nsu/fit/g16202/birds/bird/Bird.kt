package ru.nsu.fit.g16202.birds.bird

data class Bird(
    val speciesName: String,
    val description: String,
    val imageUri: String,
    val soundUri: String
) {
    override fun toString(): String = "$speciesName. $description"
}