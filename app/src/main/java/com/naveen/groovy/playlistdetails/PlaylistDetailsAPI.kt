package com.naveen.groovy.playlistdetails

import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistDetailsAPI {

    @GET("playlist_details/{id}")
    suspend fun fetchPlaylistDetails(@Path("id") id: String): PlaylistDetails

}
