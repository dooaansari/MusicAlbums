package com.app.musicalbums.contracts

interface IOnAlbumClick: IOnItemClick {
    fun onFavouriteClick(position: Int)
}