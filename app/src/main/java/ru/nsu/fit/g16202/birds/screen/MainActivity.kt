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
import ru.nsu.fit.g16202.birds.allbirds.repository.PostBird
import ru.nsu.fit.g16202.birds.allbirds.soundhandler.Base64DataLoader
import ru.nsu.fit.g16202.birds.allbirds.soundhandler.BinaryImageLoader
import ru.nsu.fit.g16202.birds.allbirds.soundhandler.DataLoader
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {

    private val loader: DataLoader = Base64DataLoader()
    private val imageLoader: DataLoader = BinaryImageLoader()

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
                        supportFragmentManager.fragments.forEach {
                            try {
                                val rep = (it as BirdFragment).repositoryProvider?.repository!!

                                val nameView = dialog.findViewById<EditText>(R.id.add_name)
                                val descriptionView = dialog.findViewById<EditText>(R.id.add_description)
                                val imageLinkView = dialog.findViewById<EditText>(R.id.add_image_link)
                                val soundLinkView = dialog.findViewById<EditText>(R.id.add_sound_link)


                                val soundLink = soundLinkView.text.toString()
                                val imageLink = imageLinkView.text.toString()



                                rep.addBird(
                                    PostBird(
                                        nameView.text.toString(),
                                        descriptionView.text.toString(),
                                        soundLink to imageLoader.loadData(soundLink),
                                        imageLink to imageLoader.loadData(imageLink)
                                    )
                                )
                            } catch (e: IllegalStateException) {
                                Toast.makeText(this, R.string.nullError, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }

                dialog.show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
