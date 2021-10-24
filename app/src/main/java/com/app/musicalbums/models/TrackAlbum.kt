package com.app.musicalbums.models

import com.app.musicalbums.enums.ImageSize
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrackAlbum(

    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("playcount")
    @Expose
    var playcount: Int? = null,
    @SerializedName("mbid")
    @Expose
    var mbid: String? = null,
    @SerializedName("url")
    @Expose
    var url: String? = null,
    @SerializedName("artist")
    @Expose
    var artist: String? = null,
    @SerializedName("image")
    @Expose
    var images: List<Image>? = null,
    @SerializedName("tracks")
    @Expose
    var tracks: Tracks? = null,

    @Transient
    var image: String? = images?.find { it.size == ImageSize.medium.name }?.text,
    @Transient
    var artistId: Long? = 0
) {
    constructor(id: Int?, name: String?, mbid: String?, image: String?, artistId: Long?)
            : this( name, 0, mbid, null, null, null, null, image, artistId)
}