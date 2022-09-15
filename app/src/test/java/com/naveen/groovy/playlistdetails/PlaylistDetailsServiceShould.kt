package com.naveen.groovy.playlistdetails

import com.naveen.groovy.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistDetailsServiceShould : BaseUnitTest() {

    val api: PlaylistDetailsAPI = mock()
    private val id = "1"
    private val playlistDetails: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Damn Backend Developer")

    @Test
    fun fetchPlaylistDetailsFromAPI() = runBlockingTest {
        val service = mockSuccessCase()
        service.getPlaylistDetails(id).single()
        verify(api, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        val service = mockSuccessCase()
        assertEquals(expected, service.getPlaylistDetails(id).single())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        val service = mockErrorCase()
        assertEquals(
            "Something went wron",
            service.getPlaylistDetails(id).first().exceptionOrNull()?.message
        )
    }

    private fun mockSuccessCase(): PlaylistDetailsService {
        runBlocking {
            whenever(api.fetchPlaylistDetails(id))
                .thenReturn(
                    playlistDetails
                )
        }
        return PlaylistDetailsService(api)
    }

    private fun mockErrorCase(): PlaylistDetailsService {
        runBlocking {
            whenever(api.fetchPlaylistDetails(id)).thenThrow(exception)
        }
        return PlaylistDetailsService(api)
    }


}