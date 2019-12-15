package ru.nsu.fit.g16202.birds.screen

import ru.nsu.fit.g16202.birds.allbirds.repository.BirdsRepository

interface RepositoryProvider {
    val repository: BirdsRepository?
}