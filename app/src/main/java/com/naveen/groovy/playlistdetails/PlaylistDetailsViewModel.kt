package com.naveen.groovy.playlistdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaylistDetailsViewModel @Inject constructor(private val service: PlaylistDetailsService) :
    ViewModel() {

    val playlistDetails: MutableLiveData<Result<PlaylistDetails>> = MutableLiveData()

    val loader = MutableLiveData<Boolean>()

    fun getPlaylistDetails(id: String) {
        viewModelScope.launch {
            loader.postValue(true)
            service.getPlaylistDetails(id)
                .onEach { loader.postValue(false) }
                .collect {
                    playlistDetails.postValue(it)
                }
        }
    }

}
