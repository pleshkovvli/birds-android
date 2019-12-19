package ru.nsu.fit.g16202.birds.allbirds.repository

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.*
import ru.nsu.fit.g16202.birds.bird.entity.Bird

class MainBirdsRepository(
    private val allBirdsEndpoint: String,
    private val fileEndpoint: String
) : BirdsRepository {

    private val birdsWaited: Deferred<List<Bird>?>

    private var listLoaded = false

    override var birds: List<Bird> = emptyList()
        get() = if (listLoaded) {
            field
        } else {
            listLoaded = true
            field = runBlocking { birdsWaited.await() } ?: emptyList()
            field
        }

    init {
        birdsWaited = CoroutineScope(Dispatchers.IO).async {
            val (_, _, result) = allBirdsEndpoint
                .httpGet()
                .responseString()

            when (result) {
                is com.github.kittinunf.result.Result.Failure -> {
                    throw result.getException()
                }
                is com.github.kittinunf.result.Result.Success -> {
                    val data: String = result.get()
                    val apiBirds = Klaxon().parse<BirdsAPI>(data)!!.birds

                    apiBirds.map { birdFromAPI(it) }
                }
            }
        }
    }

    private fun birdFromAPI(apiBird: BirdAPI): Bird = Bird(
        apiBird.id,
        apiBird.name,
        apiBird.description,
        "$fileEndpoint/${apiBird.imageFileId}",
        "$fileEndpoint/${apiBird.audioFileId}"
    )

    private class BirdsAPI(val birds: List<BirdAPI>)
}