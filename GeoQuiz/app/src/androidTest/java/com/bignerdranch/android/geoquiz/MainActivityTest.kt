package com.bignerdranch.android.geoquiz

import androidx.test.core.app.ActivityScenario
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.action.ViewActions.click

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun showsFirstQuestionOnLaunch() {
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.question_australia)))
    }

    @Test
    fun showsSecondQuestionAfterNextPress() {
        onView(withId(R.id.next_button)).perform(click())
        onView(withId(R.id.question_text_view))
    }
}