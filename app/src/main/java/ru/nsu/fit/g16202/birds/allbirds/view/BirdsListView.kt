package ru.nsu.fit.g16202.birds.allbirds.view


import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.birdsandroid.R
import kotlinx.android.synthetic.main.description_popup.view.*
import kotlinx.android.synthetic.main.fragment_bird.view.*
import kotlinx.android.synthetic.main.image_popup.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nsu.fit.g16202.birds.bird.imagehandler.ImageHandler
import ru.nsu.fit.g16202.birds.bird.imagehandler.ImageShow
import ru.nsu.fit.g16202.birds.bird.view.BirdView


class BirdsListView(
    private val createImageHandler: (() -> ImageView) -> ImageHandler
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

        private var imageToShow: ImageView = mImageView

        private var isPlaying: Boolean = false
        private var isLoading: Boolean = false

        private var onPlayListener: (() -> Unit)? = null
        private var onStopListener: (() -> Unit)? = null

        private var onLoadImage: (() -> Unit)? = null

        private lateinit var imageShow: ImageShow

        init {
            mSoundButton.setOnClickListener {
                if(isPlaying) {
                    onStopListener?.invoke()
                } else {
                    onPlayListener?.invoke()
                }
            }

            mContentView.setOnClickListener {
                createTextPopup(description)
            }

            mNameView.setOnClickListener {
                createTextPopup(name)
            }

            mImageView.setOnClickListener {
                val inflater = mView.context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                val popupView = inflater!!.inflate(R.layout.image_popup, null)

                val popupWindow = createPopupWindow(popupView)

                imageToShow = popupView.full_image
                onLoadImage?.invoke()
                showImage()
                imageToShow = mImageView

                popupView.setOnTouchListener { _, _ ->
                    popupWindow.dismiss()
                    true
                }
            }
        }

        private fun createTextPopup(value: String) {
            val inflater =
                mView.context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater?
            val popupView = inflater!!.inflate(R.layout.description_popup, null)

            val popupWindow = createPopupWindow(popupView)
            popupView.description.text = value

            popupView.setOnTouchListener { _, _ ->
                popupWindow.dismiss()
                true
            }
        }

        private fun createPopupWindow(popupView: View?): PopupWindow {
            val width = LinearLayout.LayoutParams.WRAP_CONTENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            val focusable = true
            val popupWindow = PopupWindow(popupView, width, height, focusable)

            popupWindow.showAtLocation(mView, Gravity.CENTER, 0, 0)
            return popupWindow
        }

        override var fillView: (() -> Unit)? = null

        override var name: String = ""
            set(value) {
                field = value
                mNameView.text = value
            }
        override var description: String = ""
            set(value) {
                field = value
                mContentView.text = value
            }

        override fun setOnPlaySoundListener(listener: (() -> Unit)?) {
            onPlayListener = listener
        }

        override fun setOnStopSoundListener(listener: (() -> Unit)?) {
            onStopListener = listener
        }

        override fun setOnLoadImageListener(listener: (() -> Unit)?) {
            onLoadImage = listener
        }

        override fun getImageHandler(): ImageHandler {
            return createImageHandler { imageToShow }.also { imageShow = it }
        }

        override fun showImage() {
            imageShow.showImage()
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
