package ru.nsu.fit.g16202.birds.allbirds.repository

import junit.framework.Assert.fail
import org.junit.Test

class MainBirdsRepositoryTest {

    @Test(expected = com.github.kittinunf.fuel.core.FuelError::class)
    fun testNetworkFailure() {
        val repository = MainBirdsRepository("http://0.0.0.0:1234/api/v1/birds/fail")
        val birds = repository.birds
        fail()
    }
}