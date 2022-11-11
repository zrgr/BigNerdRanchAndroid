package com.example.android.criminalintent

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import junit.framework.Assert.assertTrue


@RunWith(AndroidJUnit4::class)
class CrimeDetailFragmentTest {

    private lateinit var scenario: FragmentScenario<CrimeDetailFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer()
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun crimeIsSolvedWhenCrimeSolvedIsClicked() {
        onView(withId(R.id.crime_solved)).perform(click())
        scenario.onFragment{ fragment ->
            assertTrue(fragment.crime.isSolved)
        }
    }

}