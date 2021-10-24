package com.app.musicalbums.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.musicalbums.adapters.TracksAdapter
import com.app.musicalbums.base.BaseFragment
import com.app.musicalbums.databinding.AlbumDetailsFragmentBinding
import com.app.musicalbums.features.albums.AlbumsFragmentArgs
import com.app.musicalbums.helpers.loadAlbumImage
import com.app.musicalbums.helpers.loadImage
import kotlinx.coroutines.launch

class AlbumDetailsFragment : BaseFragment<AlbumDetailsFragmentBinding>() {

    var tracksAdapter: TracksAdapter = TracksAdapter()

    override val viewModel: AlbumDetailsViewModel by viewModels()

    val args: AlbumDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AlbumDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArgumentsData()
        setRecyclerView()
    }


    private fun setRecyclerView() {
        binding.tracks.apply {
            adapter = tracksAdapter
            layoutManager = LinearLayoutManager(context)
        }
        lifecycleScope.launch {
            tracksAdapter.submitList(
                args.tracks.toList()
            )
        }
        binding.noTracks.isVisible = args.tracks.isEmpty()
        binding.tracks.isVisible = !args.tracks.isEmpty()
    }

    fun setArgumentsData() {
        binding.artistName.text = args.artist
        binding.image.loadImage(args.albumImage)

    }

}