package com.naveen.groovy.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.naveen.groovy.databinding.FragmentPlaylistBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PlaylistViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PlaylistFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observePlaylists()

        observeLoader()
    }

    private fun observeLoader() {
        viewModel.loader.observe(viewLifecycleOwner) { isLoading ->
            when (isLoading) {
                true -> binding.loader.visibility = VISIBLE
                false -> binding.loader.visibility = GONE
            }
        }
    }

    private fun observePlaylists() {
        viewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null)
                createPlaylistRv(playlists.getOrNull()!!)
        }
    }

    private fun createPlaylistRv(playlists: List<Playlist>) {
        binding.playlistsList.adapter = PlaylistAdapter(playlists){
            val action = PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}