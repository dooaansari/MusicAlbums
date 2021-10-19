package com.app.musicalbums.adapters.viewholders

import android.view.View
import com.app.musicalbums.base.BaseViewHolder
import com.app.musicalbums.databinding.ArtistRecyclerRowBinding
import com.app.musicalbums.models.Artist

class ArtistViewHolder(private val binding : ArtistRecyclerRowBinding): BaseViewHolder(binding.root) {
    override fun bindView(data: Any?) {
       if(data is Artist){
           with(binding){
               with(data){
                   artistName.text = name
               }

           }
       }
    }


}