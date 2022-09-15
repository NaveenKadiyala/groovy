package com.naveen.groovy.playlist

import com.naveen.groovy.R
import javax.inject.Inject

class PlaylistMapper @Inject constructor() : Function1<List<PlaylistRaw>, List<Playlist>> {

    override fun invoke(playlistRaw: List<PlaylistRaw>): List<Playlist> {
        return playlistRaw.map {
            Playlist(
                it.id,
                it.name,
                it.category,
                if (it.category == "rock") R.drawable.rock else R.mipmap.ic_launcher
            )
        }
    }

}
