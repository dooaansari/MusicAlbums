package com.app.musicalbums.adapters.viewholders

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.app.musicalbums.base.BaseViewHolder
import com.app.musicalbums.contracts.IOnItemClick
import com.app.musicalbums.databinding.ArtistRecyclerErrorFooterBinding
import com.app.musicalbums.network.exceptions.runTimeExceptionParser

class LoadStateHolder (private val binding : ArtistRecyclerErrorFooterBinding): BaseViewHolder(binding.root) {
    override fun bindView(data: Any?, onItemClick: IOnItemClick?) {
        if(data is LoadState){
            with(binding) {
                    if(data is LoadState.Error){
                        errorMessage.text = binding.errorMessage.context.getString(runTimeExceptionParser((data).error))
                    }
                    loader.isVisible = data is LoadState.Loading
                    errorMessage.isVisible = data is LoadState.Error
            }
        }

    }


}