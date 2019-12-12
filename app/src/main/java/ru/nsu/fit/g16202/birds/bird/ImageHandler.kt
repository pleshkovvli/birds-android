package ru.nsu.fit.g16202.birds.bird

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager

class ImageHandler(private val getManager: () -> RequestManager) : ImageLoader, ImageShow {
    private lateinit var requestBuilder : RequestBuilder<Drawable>

    override fun loadImage(uri: String) {
        requestBuilder = getManager().load(uri)
    }

    override fun showImage(imageView: ImageView) {
        requestBuilder
            .centerCrop()
            .into(imageView)
    }
}