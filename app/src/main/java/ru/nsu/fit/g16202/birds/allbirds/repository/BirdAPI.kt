package ru.nsu.fit.g16202.birds.allbirds.repository

data class BirdAPI(
    val id: String,
    val name: String,
    val description: String,
    val audioFileId: String,
    val imageFileId: String
)