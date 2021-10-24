package com.app.musicalbums.adapters.viewholders

import android.view.View
import com.app.musicalbums.R
import com.app.musicalbums.base.BaseViewHolder
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.ArtistRecyclerRowBinding
import com.app.musicalbums.databinding.TrackRecyclerRowBinding
import com.app.musicalbums.models.Artist
import com.app.musicalbums.models.Track

class TrackViewHolder(private val binding: TrackRecyclerRowBinding) :
    BaseViewHolder(binding.root) {

    override fun bindView(data: Any?, onItemClick: IOnItemClick?) {
        if (data is Track) {
            with(binding) {
                with(data) {
                    binding.trackName.text = name
                    binding.trackUrl.text = url
                    binding.duration.text =
                        duration.toString()
                }

            }
        }
    }
}