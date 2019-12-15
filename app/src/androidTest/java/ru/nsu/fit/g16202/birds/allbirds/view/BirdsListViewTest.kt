package ru.nsu.fit.g16202.birds.allbirds.view

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.birdsandroid.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.nsu.fit.g16202.birds.screen.BirdFragment
import ru.nsu.fit.g16202.birds.screen.MainActivity
import androidx.test.espresso.ViewAction
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.espresso.UiController
import androidx.test.espresso.action.ViewActions.click
import org.hamcrest.Matcher
import ru.nsu.fit.g16202.birds.allbirds.repository.BirdsRepository
import ru.nsu.fit.g16202.birds.screen.RepositoryProvider


class BirdsListViewTest {

    @get:Rule
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun init() {
        activityRule.activity
            .supportFragmentManager.beginTransaction()
    }

    @Test
    fun testSounds() {
        val fragmentArgs = Bundle().apply {
            putInt("selectedListItem", 0)
        }

        val fragmentFactory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                val fragment = super.instantiate(classLoader, className) as BirdFragment
                fragment.repositoryProvider = object : RepositoryProvider {
                    override val repository: BirdsRepository? = MockedBirdsRepository
                }

                return fragment
            }
        }

        launchFragmentInContainer<BirdFragment>(fragmentArgs, factory = fragmentFactory)

        fun clickOnViewById(viewId: Int) = object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v: View = view.findViewById(viewId)
                v.performClick()
            }
        }

        Thread.sleep(4000)

        onView(withId(R.id.birds_list))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<BirdsListView.ViewHolder>(
                        0, clickOnViewById(R.id.sound_image)
                    )
            )

        onView(withId(R.id.birds_list))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<BirdsListView.ViewHolder>(
                        1, clickOnViewById(R.id.content)
                    )
            )

        Thread.sleep(4000)

        onView(withId(R.id.description))
            .perform(click())

        onView(withId(R.id.birds_list))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<BirdsListView.ViewHolder>(
                        0, clickOnViewById(R.id.sound_image)
                    )
            )


        Thread.sleep(1000)

        onView(withId(R.id.birds_list))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<BirdsListView.ViewHolder>(
                        1, clickOnViewById(R.id.sound_image)
                    )
            )

        Thread.sleep(700)

        onView(withId(R.id.birds_list))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<BirdsListView.ViewHolder>(
                        2, clickOnViewById(R.id.sound_image)
                    )
            )

        onView(withId(R.id.birds_list))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<BirdsListView.ViewHolder>(
                        1, clickOnViewById(R.id.item_image)
                    )
            )

        Thread.sleep(4000)

        onView(withId(R.id.full_image))
            .perform(click())

        onView(withId(R.id.birds_list))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<BirdsListView.ViewHolder>(
                        3, clickOnViewById(R.id.sound_image)
                    )
            )

        Thread.sleep(4000)
    }

}