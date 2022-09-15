package com.naveen.groovy.playlist

import com.naveen.groovy.R
import com.naveen.groovy.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PlaylistMapperShould : BaseUnitTest() {

    private val playlistRaw = PlaylistRaw("1", "Naveen", "Human")
    private val playlistRawRock = PlaylistRaw("1", "Naveen", "rock")
    private val mapper = PlaylistMapper()
    private val playlists: List<Playlist> = mapper(listOf(playlistRaw))
    private val playlist = playlists.first()
    private val playlistRock = mapper(listOf(playlistRawRock))[0]

    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory() {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.mipmap.ic_launcher, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory() {
        assertEquals(R.drawable.rock, playlistRock.image)
    }
}