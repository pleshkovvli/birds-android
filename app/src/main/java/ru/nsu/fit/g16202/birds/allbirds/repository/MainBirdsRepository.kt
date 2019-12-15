package ru.nsu.fit.g16202.birds.allbirds.repository

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.*
import ru.nsu.fit.g16202.birds.bird.entity.Bird

class MainBirdsRepository(private val endpoint: String) : BirdsRepository {

    private val birdsWaited: Deferred<List<Bird>?>

    init {
        birdsWaited = CoroutineScope(Dispatchers.IO).async {
            val (request, response, result) = endpoint
                .httpGet()
                .responseString()

            when (result) {
                is com.github.kittinunf.result.Result.Failure -> {
                    throw result.getException()
                }
                is com.github.kittinunf.result.Result.Success -> {
                    val data: String = result.get()
                    Klaxon().parseArray<Bird>(data)
                }
            }
        }
    }

    private var listLoaded = false

    override var birds: List<Bird> = emptyList()
        get() = if(listLoaded) { field } else {
            listLoaded = true
            field = runBlocking { birdsWaited.await() } ?: emptyList()
            field
        }
}