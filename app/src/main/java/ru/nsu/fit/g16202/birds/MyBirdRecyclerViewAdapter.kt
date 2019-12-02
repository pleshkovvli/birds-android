package ru.nsu.fit.g16202.birds

import android.media.MediaPlayer
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.birdsandroid.R


import ru.nsu.fit.g16202.birds.BirdFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_bird.view.*
import ru.nsu.fit.g16202.birds.model.Bird

/**
 * [RecyclerView.Adapter] that can display a [Bird] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyBirdRecyclerViewAdapter(
    private val mValues: List<Bird>,
    private val mListener: OnListFragmentInteractionListener?,
    private val mImageLoader: (Bird, ImageView) -> Unit,
    private val playSound: (Bird) -> Unit,
    private val stopSound: () -> Unit
) : RecyclerView.Adapter<MyBirdRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Bird
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_bird, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        mImageLoader(item, holder.mImageView)
        holder.mNameView.text = item.speciesName
        holder.mContentView.text = item.description
        holder.mSoundButton.setOnClickListener { playSound(item) }
        holder.mSoundButton.setOnLongClickListener { stopSound(); true }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }



    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageView: ImageView = mView.item_image
        val mNameView: TextView = mView.item_name
        val mContentView: TextView = mView.content
        val mSoundButton: ImageButton = mView.sound_image

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
