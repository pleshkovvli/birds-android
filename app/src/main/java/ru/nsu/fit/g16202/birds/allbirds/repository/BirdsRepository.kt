package ru.nsu.fit.g16202.birds.allbirds.repository

import ru.nsu.fit.g16202.birds.bird.entity.Bird

interface BirdsRepository {
    val birds: List<Bird>
}