package ru.nsu.fit.g16202.birds

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestBuilder
import com.example.birdsandroid.R


import ru.nsu.fit.g16202.birds.BirdFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_bird.view.*
import ru.nsu.fit.g16202.birds.model.Bird

/**
 * [RecyclerView.Adapter] that can display a [Bird] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class BirdsView(
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<BirdsView.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    private var onBindBirdViewListener: ((BirdView, Int) -> Unit)? = null

    var itemCount: () -> Int = { -1 }

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Bird
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

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

        with(holder.mView) {
            //tag = item
            setOnClickListener(mOnClickListener)
        }
    }



    override fun getItemCount(): Int = itemCount()//mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView), BirdView {
        val mImageView: ImageView = mView.item_image
        val mNameView: TextView = mView.item_name
        val mContentView: TextView = mView.content
        val mSoundButton: ImageButton = mView.sound_image

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
