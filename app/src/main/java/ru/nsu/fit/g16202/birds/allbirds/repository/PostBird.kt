package ru.nsu.fit.g16202.birds.allbirds.repository

class PostBird(
    val name: String,
    val description: String,
    val audio: Pair<String, ByteArray>,
    val image: Pair<String, ByteArray>
)