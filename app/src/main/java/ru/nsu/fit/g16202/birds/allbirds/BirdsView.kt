package ru.nsu.fit.g16202.birds.allbirds


import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestBuilder
import com.example.birdsandroid.R
import kotlinx.android.synthetic.main.fragment_bird.view.*
import ru.nsu.fit.g16202.birds.BirdFragment.OnListFragmentInteractionListener
import ru.nsu.fit.g16202.birds.bird.Bird
import ru.nsu.fit.g16202.birds.bird.BirdView

/**
 * [RecyclerView.Adapter] that can display a [Bird] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class BirdsView : RecyclerView.Adapter<BirdsView.ViewHolder>() {

    private var onBindBirdViewListener: ((BirdView, Int) -> Unit)? = null

    var itemCount: () -> Int = { -1 }


    fun setOnBindBirdViewListener(listener: ((BirdView, Int) -> Unit)?) {
        onBindBirdViewListener = listener
    }

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
            if(listener != null) {
                mSoundButton.setOnClickListener { listener() }
            } else {
                mSoundButton.setOnClickListener(null)
            }
        }

        override fun setOnStopSoundListener(listener: (() -> Unit)?) {
            if(listener != null) {
                mSoundButton.setOnLongClickListener { listener(); true }
            } else {
                mSoundButton.setOnLongClickListener(null)
            }
        }

        override fun showImage(imageHandler: RequestBuilder<Drawable>) {
            imageHandler
                .centerCrop()
                .into(mImageView)
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
