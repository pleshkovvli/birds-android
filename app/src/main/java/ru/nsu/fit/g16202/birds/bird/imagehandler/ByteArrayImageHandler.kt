package ru.nsu.fit.g16202.birds.bird.imagehandler

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.*


class ByteArrayImageHandler(
    private val imageView: () -> ImageView
) : ImageHandler {
    private lateinit var bitmapWaited: Deferred<Bitmap>

    override fun loadImage(uri: String) {
        bitmapWaited = CoroutineScope(Dispatchers.IO).async {
            val (_, _, result) = uri
                .httpGet()
                .responseString()

            when (result) {
                is com.github.kittinunf.result.Result.Failure -> {
                    throw result.getException()
                }
                is com.github.kittinunf.result.Result.Success -> {
                    val data: String = result.get()
                    val stringData = Klaxon().parse<ImageHolder>(data)!!.data
                    val byteArray = Base64.decode(stringData, Base64.DEFAULT)
                    BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                }
            }
        }
    }

    override fun showImage() {
        runBlocking {
            val await = bitmapWaited.await()
            imageView().setImageBitmap(await)
        }
    }

    private class ImageHolder(val data: String)
}