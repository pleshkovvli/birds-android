package ru.nsu.fit.g16202.birds.screen

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.birdsandroid.R
import ru.nsu.fit.g16202.birds.allbirds.interactor.BirdsInteractor
import ru.nsu.fit.g16202.birds.allbirds.soundhandler.MediaPlayerSoundHandler
import ru.nsu.fit.g16202.birds.allbirds.interactor.BirdsListInteractor
import ru.nsu.fit.g16202.birds.allbirds.presenter.BirdsListPresenter
import ru.nsu.fit.g16202.birds.allbirds.presenter.BirdsPresenter
import ru.nsu.fit.g16202.birds.allbirds.repository.MainBirdsRepository
import ru.nsu.fit.g16202.birds.allbirds.view.BirdsListView
import ru.nsu.fit.g16202.birds.allbirds.view.BirdsView
import ru.nsu.fit.g16202.birds.bird.imagehandler.GlideImageHandler
import ru.nsu.fit.g16202.birds.bird.interactor.BirdInteractor
import ru.nsu.fit.g16202.birds.bird.presenter.BirdElementPresenter
import ru.nsu.fit.g16202.birds.bird.presenter.BirdPresenter
import ru.nsu.fit.g16202.birds.bird.view.BirdView

class BirdFragment : Fragment() {
    var repositoryProvider: RepositoryProvider? = null

    private var soundPlayer: MediaPlayer? = null

    private lateinit var birdsPresenter: BirdsPresenter

    private val birdsPresenters: MutableList<BirdPresenter> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bird_list, container, false)

        val repository = repositoryProvider?.repository ?: MainBirdsRepository

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)

                val birdsView : BirdsView = BirdsListView { imageView ->
                    GlideImageHandler(imageView) { Glide.with(this@BirdFragment) }
                }.also { adapter = it }

                val birdsInteractor : BirdsInteractor = BirdsListInteractor(
                    repository,
                    MediaPlayerSoundHandler { soundPlayer }
                )

                birdsPresenter = createBirdsPresenter(birdsInteractor, birdsView)
            }
        }
        return view
    }

    private fun createBirdsPresenter(
        birdsInteractor: BirdsInteractor,
        birdsView: BirdsView
    ) : BirdsPresenter {
        return BirdsListPresenter(birdsInteractor, birdsView) { birdView: BirdView, pos: Int ->
            val birdInteractor: BirdInteractor = birdsInteractor.createBirdInteractor(pos)
            birdsPresenters.add(
                BirdElementPresenter(birdInteractor, birdView).also { it.bindViewWithInteractor() }
            )
        }.also { it.bindViewWithInteractor() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        soundPlayer = MediaPlayer()
    }

    override fun onDetach() {
        super.onDetach()

        soundPlayer?.release()
        soundPlayer = null
    }

}
