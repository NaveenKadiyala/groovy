package com.naveen.groovy.playlistdetails

import com.naveen.groovy.utils.BaseUnitTest
import com.naveen.groovy.utils.captureValues
import com.naveen.groovy.utils.getValueForTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistDetailsViewModelShould : BaseUnitTest() {

    private val id = "1"
    private val playlistDetails = mock<PlaylistDetails>()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")
    private val service: PlaylistDetailsService = mock()

    @Test
    fun getPlaylistDetailsFromService() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)
        viewModel.playlistDetails.getValueForTest()
        verify(service, times(1)).getPlaylistDetails(id)
    }

    @Test
    fun emitPlaylistDetailsFromService() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)
        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val viewModel = mockErrorCase()
        viewModel.getPlaylistDetails(id)
        assertEquals(exception, viewModel.playlistDetails.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun showSpinnerWhileLoadingPlaylistDetails() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun hideLoaderAfterLoadingPlaylistDetails() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    private fun mockSuccessfulCase(): PlaylistDetailsViewModel {
        runBlocking {
            whenever(service.getPlaylistDetails(id))
                .thenReturn(
                    flow {
                        emit(expected)
                    }
                )
        }
        return PlaylistDetailsViewModel(service)
    }

    private fun mockErrorCase(): PlaylistDetailsViewModel {
        runBlocking {
            whenever(service.getPlaylistDetails(id)).thenReturn(
                flow { emit(Result.failure(exception)) }
            )
        }
        return PlaylistDetailsViewModel(service)
    }
}