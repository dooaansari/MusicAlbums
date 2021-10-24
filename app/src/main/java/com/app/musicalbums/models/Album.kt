package com.app.musicalbums.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.app.musicalbums.enums.ImageSize
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "album")
data class Album(
    @PrimaryKey
    @SerializedName("name")
    @Expose
    var name: String = "",
    @Ignore
    @SerializedName("playcount")
    @Expose
    var playcount: Int? = null,
    @SerializedName("mbid")
    @Expose
    var mbid: String? = null,
    @SerializedName("url")
    @Expose
    var url: String? = null,
    @Ignore
    @SerializedName("artist")
    @Expose
    var artist: Artist? = null,
    @Ignore
    @SerializedName("image")
    @Expose
    var images: List<Image>? = null,

    @Transient
    var isFavourite: Boolean = false

) {
    constructor(name: String
                , mbid: String?)
            : this(name, 0, mbid, null, null, null)
}
