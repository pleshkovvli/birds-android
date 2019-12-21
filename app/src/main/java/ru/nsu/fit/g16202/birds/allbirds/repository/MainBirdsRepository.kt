package ru.nsu.fit.g16202.birds.allbirds.repository

import android.util.Base64
import android.util.Log
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import kotlinx.coroutines.*
import ru.nsu.fit.g16202.birds.bird.entity.Bird

class MainBirdsRepository(
    private val allBirdsEndpoint: String,
    private val getFileEndpoint: String,
    private val addBirdsEndpoint: String,
    private val addFileEndpoint: String
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

    override fun addBird(newBird: PostBird) {
        CoroutineScope(Dispatchers.IO).async {
            val audioId = saveData(newBird.audio)
            val imageId = saveData(newBird.image)

            class BirdToSave(
                val name: String,
                val description: String,
                val imageFileId: String,
                val audioFileId: String
            )

            val birdToSave = BirdToSave(
                newBird.name,
                newBird.description,
                imageId,
                audioId
            )

            class BirdsHolder(val items: Array<BirdToSave>)

            val postBirds = addBirdsEndpoint
                .httpPost()

            val jsonString = Klaxon().toJsonString(
                BirdsHolder(
                    arrayOf(birdToSave)
                )
            )
            postBirds.jsonBody(
                jsonString
            )

            val (_, _, result) = postBirds.response()

            when (result) {
                is com.github.kittinunf.result.Result.Failure -> {
                    throw result.getException()
                }
                is com.github.kittinunf.result.Result.Success -> Unit
            }
        }
    }

    class SaveData(val name: String, val data: String)

    private val headersToSave: List<Pair<String, String>>
            = listOf("Content-Type" to "application/json")

    private fun saveData(dataToSave: Pair<String, ByteArray>): String {
        val httpPost = addFileEndpoint
            .httpPost()

        headersToSave.forEach {
            httpPost.headers.append(it.first, it.second)
        }

        val encodedValue = Base64.encodeToString(dataToSave.second, Base64.DEFAULT)

        val body = Klaxon().toJsonString(
            SaveData(
                dataToSave.first,
                encodedValue.toString()
            )
        )

        httpPost.jsonBody(body)


        val (_, _, result) = httpPost.responseString()

        class IdHolder (val id: String)

        return when (result) {
            is com.github.kittinunf.result.Result.Failure -> {
                throw result.getException()
            }
            is com.github.kittinunf.result.Result.Success -> {
                Klaxon().parse<IdHolder>(result.value)!!.id
            }
        }
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

                    val birdsResult = apiBirds
                        .filter {
                            it.audioFileId.first().isLetterOrDigit() and
                                it.imageFileId.first().isLetterOrDigit()
                        }
                        .map { birdFromAPI(it) }

                    birdsResult
                }
            }
        }
    }

    private fun birdFromAPI(apiBird: BirdAPI): Bird = Bird(
        apiBird.id,
        apiBird.name,
        apiBird.description,
        "$getFileEndpoint/${apiBird.imageFileId}",
        "$getFileEndpoint/${apiBird.audioFileId}"
    )

    private class BirdsAPI(val birds: List<BirdAPI>)
}