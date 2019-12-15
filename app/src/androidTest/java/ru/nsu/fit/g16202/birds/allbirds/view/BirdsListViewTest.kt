package ru.nsu.fit.g16202.birds.allbirds.view

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.birdsandroid.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.nsu.fit.g16202.birds.screen.BirdFragment
import ru.nsu.fit.g16202.birds.screen.MainActivity


class BirdsListViewTest {

    @get:Rule
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun init() {
        activityRule.activity
            .supportFragmentManager.beginTransaction()
    }

    @Test
    fun getItemCount() {
        activityRule.activity.runOnUiThread {
            //startFragment()
        }

        val fragmentArgs = Bundle().apply {
            putInt("selectedListItem", 0)
        }

        launchFragmentInContainer<BirdFragment>(fragmentArgs)

        Thread.sleep(4000)

        onView(withId(R.id.birds_list))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<BirdsListView.ViewHolder>(0, click())
            )

        Thread.sleep(4000)

        onView(withId(R.id.birds_list))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<BirdsListView.ViewHolder>(0, click())
            )

        Thread.sleep(1000)

        onView(withId(R.id.birds_list))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<BirdsListView.ViewHolder>(1, click())
            )

        Thread.sleep(100)

        onView(withId(R.id.birds_list))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<BirdsListView.ViewHolder>(2, click())
            )

        Thread.sleep(4000)

        onView(withId(R.id.birds_list))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<BirdsListView.ViewHolder>(3, click())
            )
    }

}