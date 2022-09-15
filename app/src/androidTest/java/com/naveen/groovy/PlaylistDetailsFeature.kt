package com.naveen.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.android.material.snackbar.Snackbar
import com.naveen.groovy.playlist.idlingResource
import com.naveen.groovy.playlistdetails.PlaylistDetails
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import org.hamcrest.core.AllOf.allOf
import org.junit.Test

class PlaylistDetailsFeature : BaseUITest() {

    private val playlistDetails = PlaylistDetails(
        "1",
        "Hard Rock Cafe",
        "Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door"
    )

    @Test
    fun displaysPlaylistNameAndDetails() {
        navigateToDetailsScreen()
        assertDisplayed(playlistDetails.name)
        assertDisplayed(playlistDetails.details)
    }

    @Test
    fun displaysLoaderWhileFetchingPlaylistDetails() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(2000)
        navigateToDetailsScreen()
        assertDisplayed(R.id.playlist_details_loader)
    }

    @Test
    fun hideLoader() {
        navigateToDetailsScreen()
        assertNotDisplayed(R.id.playlist_details_loader)
    }

    @Test
    fun displaysErrorMessageWhenNetworkFails() {
        navigateToDetailsScreen(1)
        assertDisplayed(R.string.generic_error)
    }

    @Test
    fun hideErrorMessageAfterItsDuration() {
        navigateToDetailsScreen(1)
        Thread.sleep(3000)
        assertNotExist(R.string.generic_error)
    }

    private fun navigateToDetailsScreen(childPos: Int = 0) {
        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlists_list), childPos))
            )
        ).perform(click())
    }
}