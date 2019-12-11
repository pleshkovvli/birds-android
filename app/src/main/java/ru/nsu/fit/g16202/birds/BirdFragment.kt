package ru.nsu.fit.g16202.birds

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
import ru.nsu.fit.g16202.birds.allbirds.BirdsInteractor
import ru.nsu.fit.g16202.birds.allbirds.BirdsPresenter
import ru.nsu.fit.g16202.birds.allbirds.BirdsView

class BirdFragment : Fragment() {

    private var columnCount = 1

    private var soundPlayer: MediaPlayer? = null

    private lateinit var birdsPresenter: BirdsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bird_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                val birdsView = BirdsView()
                adapter = birdsView

                val birdsInteractor = BirdsInteractor(
                    { soundPlayer },
                    { Glide.with(this@BirdFragment) }
                )
                birdsPresenter = BirdsPresenter(birdsInteractor, birdsView)
            }
        }
        return view
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


    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            BirdFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
