package ru.nsu.fit.g16202.birds.allbirds.repository

import android.util.Base64
import android.util.Log
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import kotlinx.coroutines.*
import ru.nsu.fit.g16202.birds.bird.entity.Bird
import java.nio.charset.Charset

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
            Log.d("TEST_TEST", "TEST!")
            val audioId = saveData(newBird.audio)
            Log.d("TEST_TEST", "TEST!!!!")
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

            Log.d("TEST_TEST", "$result")

            when (result) {
                is com.github.kittinunf.result.Result.Failure -> {
                    throw result.getException()
                }
                is com.github.kittinunf.result.Result.Success -> Unit
            }
        }
    }

    class SaveData(@Json(name = "name") val name: String,@Json(name = "data") val data: String)

    private fun saveData(value: Pair<String, ByteArray>): String {
        val httpPost = addFileEndpoint
            .httpPost()

        httpPost.headers.append("Content-Type", "application/json")

        val newValue = Base64.encodeToString(value.second, Base64.DEFAULT)

        val json = """{
           "Name": "${value.first}",
           "Data": "$newValue"
        }
        """.trimIndent()


        val body = Klaxon().toJsonString(
            SaveData(
                value.first,
                newValue.toString()
            )
        )
        httpPost.jsonBody(
            body
        )


        val (_, _, result) = httpPost.responseString()

        Log.d("TEST_TEST", "$result")

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
                            !(it.audioFileId.startsWith("{") or
                                    it.imageFileId.startsWith("{"))
                        }
                        .map { birdFromAPI(it) }

                    birdsResult
                }
            }
        }
    }//Воробей - это птица

    private fun birdFromAPI(apiBird: BirdAPI): Bird = Bird(
        apiBird.id,
        apiBird.name,
        apiBird.description,
        "$getFileEndpoint/${apiBird.imageFileId}",
        "$getFileEndpoint/${apiBird.audioFileId}"
    )

    private class BirdsAPI(val birds: List<BirdAPI>)
}