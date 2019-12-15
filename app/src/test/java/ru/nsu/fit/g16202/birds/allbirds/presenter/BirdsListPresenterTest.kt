package ru.nsu.fit.g16202.birds.allbirds.presenter

import org.junit.Test

import org.junit.Assert.*
import ru.nsu.fit.g16202.birds.allbirds.interactor.BirdsInteractor
import ru.nsu.fit.g16202.birds.allbirds.view.BirdsView
import ru.nsu.fit.g16202.birds.bird.imagehandler.ImageHandler
import ru.nsu.fit.g16202.birds.bird.interactor.BirdInteractor
import ru.nsu.fit.g16202.birds.bird.view.BirdView

class BirdsListPresenterTest {

    @Test
    fun bindViewWithInteractor() {
        val sizeValue = 2345

        val interactor = object : BirdsInteractor {
            override val size: Int
                get() = sizeValue

            override fun createBirdInteractor(position: Int): BirdInteractor {
                throw NotImplementedError()
            }
        }

        val view = object : BirdsView {
            var onBindlistener: ((BirdView, Int) -> Unit)? = null

            override fun setOnBindBirdViewListener(
                listener: ((BirdView, Int) -> Unit)?
            ) {
                onBindlistener = listener
            }

            override var itemCount: () -> Int = { -1 }
        }


        var listenerHadBeenCalled = false
        val presenter = BirdsListPresenter(interactor, view) { _, _ ->
            listenerHadBeenCalled = true
        }

        assertEquals(view.itemCount(), -1)

        presenter.bindViewWithInteractor()
        view.onBindlistener!!.invoke(object : BirdView {
            override var fillView: (() -> Unit)? = null
            override var name: String = ""
            override var description: String = ""

            override fun getImageHandler(): ImageHandler
                    = throw NotImplementedError()

            override fun setOnPlaySoundListener(
                listener: (() -> Unit)?
            ) = throw NotImplementedError()

            override fun setOnStopSoundListener(
                listener: (() -> Unit)?
            ) = throw NotImplementedError()

            override fun setOnLoadImageListener(
                listener: (() -> Unit)?
            ) = throw NotImplementedError()

            override fun loadSound() = throw NotImplementedError()
            override suspend fun playSound() = throw NotImplementedError()

            override fun stopSound() = throw NotImplementedError()
            override fun showImage() = throw NotImplementedError()

        }, 1)

        assertTrue(listenerHadBeenCalled)
        assertEquals(view.itemCount(), sizeValue)
    }
}