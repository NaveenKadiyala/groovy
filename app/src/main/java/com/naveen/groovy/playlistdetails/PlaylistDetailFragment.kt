package com.naveen.groovy.playlistdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.naveen.groovy.R
import com.naveen.groovy.databinding.FragmentPlaylistDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    private lateinit var id: String
    private var _binding: FragmentPlaylistDetailBinding? = null
    private val binding get() = _binding!!
    private val args: PlaylistDetailFragmentArgs by navArgs()

    private lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistDetailsViewModel::class.java]
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistDetailFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = args.playlistId

        viewModel.getPlaylistDetails(id)

        observePlaylistDetails()

        observeLoader()
    }

    private fun observeLoader() {
        viewModel.loader.observe(viewLifecycleOwner) { isLoading ->
            when (isLoading) {
                true -> binding.playlistDetailsLoader.visibility = VISIBLE
                false -> binding.playlistDetailsLoader.visibility = GONE
            }
        }
    }

    private fun observePlaylistDetails() {
        viewModel.playlistDetails.observe(viewLifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                bindDataToView(playlistDetails)
            } else {
                Snackbar.make(binding.root, R.string.generic_error, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun bindDataToView(playlistDetails: Result<PlaylistDetails>) {
        binding.playlistDetailsName.text = playlistDetails.getOrNull()!!.name
        binding.playlistDetails.text = playlistDetails.getOrNull()!!.details
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}