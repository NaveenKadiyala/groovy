package com.naveen.groovy.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naveen.groovy.R
import com.naveen.groovy.databinding.ItemPlaylistBinding

class PlaylistAdapter(
    private val playlistItems: List<Playlist>,
    private val listener: (String) -> Unit
) :
    RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    inner class PlaylistViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { listener(playlistItems[adapterPosition].id) }
        }

        fun bind(playlist: Playlist) {
            binding.apply {
                playlistName.text = playlist.name
                playlistCategory.text = playlist.category
                playlistImage.setImageResource(playlist.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .also { return PlaylistViewHolder(it) }
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlistItems[position])
    }

    override fun getItemCount() = playlistItems.size
}