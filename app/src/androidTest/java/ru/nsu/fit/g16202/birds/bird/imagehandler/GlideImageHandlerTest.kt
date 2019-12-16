package ru.nsu.fit.g16202.birds.bird.imagehandler

import org.junit.Test

class GlideImageHandlerTest {

    @Test(expected = UninitializedPropertyAccessException::class)
    fun testImageHandlerFail() {
        val handler = GlideImageHandler(
            { throw NotImplementedError() }, { throw NotImplementedError() }
        )

        handler.showImage()
    }
}
