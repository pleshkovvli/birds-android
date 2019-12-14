package ru.nsu.fit.g16202.birds.bird.imagehandler

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager

class GlideImageHandler(
    private val imageView: ImageView,
    private val getManager: () -> RequestManager
) : ImageHandler {
    private lateinit var requestBuilder : RequestBuilder<Drawable>

    override fun loadImage(uri: String) {
        requestBuilder = getManager().load(uri)
    }

    override fun showImage() {
        requestBuilder
            .centerCrop()
            .into(imageView)
    }
}