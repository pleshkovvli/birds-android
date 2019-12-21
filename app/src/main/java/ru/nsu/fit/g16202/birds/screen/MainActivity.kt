package ru.nsu.fit.g16202.birds.screen

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.birdsandroid.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nsu.fit.g16202.birds.allbirds.repository.BirdsRepository
import ru.nsu.fit.g16202.birds.allbirds.repository.PostBird
import ru.nsu.fit.g16202.birds.allbirds.soundhandler.BinaryImageLoader
import ru.nsu.fit.g16202.birds.allbirds.soundhandler.DataLoader
import java.lang.Exception
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {

    private val loader: DataLoader = BinaryImageLoader()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_bird -> {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.add_bird)
                dialog.setTitle(R.string.add_bird_title)


                dialog.setOnShowListener {
                    val button = dialog.findViewById<Button>(R.id.confirm_button)
                    button.setOnClickListener {
                        supportFragmentManager
                            .fragments
                            .filterIsInstance<BirdFragment>()
                            .first()
                            .also { uploadBird(dialog, it) }
                    }
                }

                dialog.show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun uploadBird(
        dialog: Dialog,
        birdFragment: BirdFragment
    ) {
        try {
            val nameView = dialog.findViewById<EditText>(R.id.add_name)
            val descriptionView =
                dialog.findViewById<EditText>(R.id.add_description)
            val imageLinkView =
                dialog.findViewById<EditText>(R.id.add_image_link)
            val soundLinkView =
                dialog.findViewById<EditText>(R.id.add_sound_link)


            val soundLink = soundLinkView.text.toString()
            val imageLink = imageLinkView.text.toString()

            val postBird = PostBird(
                nameView.text.toString(),
                descriptionView.text.toString(),
                soundLink to loader.loadData(soundLink),
                imageLink to loader.loadData(imageLink)
            )

            val birdsRepository =
                birdFragment.repositoryProvider.repository!!

            uploadBird(birdsRepository, postBird)
            dialog.dismiss()
        } catch (e: IllegalStateException) {
            Toast.makeText(
                this,
                R.string.nullError,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun uploadBird(
        rep: BirdsRepository,
        postBird: PostBird
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                rep.addBird(postBird)
                Toast.makeText(
                    this@MainActivity,
                    R.string.uploadBirdSuccess,
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    R.string.uploadBirdError,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
