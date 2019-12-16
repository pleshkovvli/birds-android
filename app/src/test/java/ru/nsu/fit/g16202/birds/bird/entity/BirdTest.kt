package ru.nsu.fit.g16202.birds.bird.entity

import org.junit.Test

import org.junit.Assert.*
import ru.nsu.fit.g16202.birds.allbirds.repository.MockedBirdsRepository

class BirdTest {

    @Test
    fun toStringTest() {
        val bird = MockedBirdsRepository.birds[3]
        assertEquals(bird.toString(), "${bird.name}. ${bird.description}")
    }
}