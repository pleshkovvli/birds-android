package ru.nsu.fit.g16202.birds.allbirds.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.birdsandroid.R
import kotlinx.android.synthetic.main.fragment_bird.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nsu.fit.g16202.birds.bird.imagehandler.ImageHandler
import ru.nsu.fit.g16202.birds.bird.imagehandler.ImageShow
import ru.nsu.fit.g16202.birds.bird.view.BirdView

class BirdsListView(
    private val createImageHandler: (ImageView) -> ImageHandler
) : BirdsView, RecyclerView.Adapter<BirdsListView.ViewHolder>() {

    private var onBindBirdViewListener: ((BirdView, Int) -> Unit)? = null

    override fun setOnBindBirdViewListener(listener: ((BirdView, Int) -> Unit)?) {
        onBindBirdViewListener = listener
    }

    override var itemCount: () -> Int = { -1 }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_bird, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBindBirdViewListener?.invoke(holder, position)
        holder.fillView?.invoke()
    }

    override fun getItemCount(): Int = itemCount()

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView), BirdView {
        private val mImageView: ImageView = mView.item_image
        private val mNameView: TextView = mView.item_name
        private val mContentView: TextView = mView.content
        private val mSoundButton: ImageButton = mView.sound_image

        private var isPlaying: Boolean = false
        private var isLoading: Boolean = false

        private var onPlayListener: (() -> Unit)? = null
        private var onStopListener: (() -> Unit)? = null

        private lateinit var imageShow: ImageShow

        init {
            mSoundButton.setOnClickListener {
                if(isPlaying) {
                    onStopListener?.invoke()
                } else {
                    onPlayListener?.invoke()
                }
            }
        }

        override var fillView: (() -> Unit)? = null

        override var name: String
            get() = mNameView.text.toString()
            set(value) {
                mNameView.text = value
            }
        override var description: String
            get() = mContentView.text.toString()
            set(value) {
                mContentView.text = value
            }

        override fun setOnPlaySoundListener(listener: (() -> Unit)?) {
            onPlayListener = listener
        }

        override fun setOnStopSoundListener(listener: (() -> Unit)?) {
            onStopListener = listener
        }

        override fun getImageHandler(): ImageHandler {
            return createImageHandler(mImageView).also { imageShow = it }
        }

        override fun showImage() {
            imageShow.showImage()
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }

        override fun loadSound() {
            isLoading = true
            mSoundButton.setImageResource(R.drawable.ic_placeholder)
        }

        override suspend fun playSound() = withContext(Dispatchers.Main) {
            if(isLoading) {
                isPlaying = true
                isLoading = false
                mSoundButton.setImageResource(R.drawable.ic_action_name)
            }
        }

        override fun stopSound() {
            isPlaying = false
            isLoading = false
            mSoundButton.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        }
    }
}
