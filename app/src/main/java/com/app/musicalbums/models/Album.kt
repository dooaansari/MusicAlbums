package com.app.musicalbums.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.app.musicalbums.enums.ImageSize
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "album")
data class Album(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    val name: String,
    @Ignore
    @SerializedName("playcount")
    @Expose
    val playcount: Int,
    @SerializedName("mbid")
    @Expose
    val mbid: String,
    @Ignore
    @SerializedName("url")
    @Expose
    val url: String,
    @Ignore
    @SerializedName("artist")
    @Expose
    val artist: Artist,
    @Ignore
    @SerializedName("image")
    @Expose
    val images: List<Image>? = null,
    @Ignore
    @SerializedName("tracks")
    @Expose
    val tracks: Tracks? = null,

    val image: String? = images?.find { it.size == ImageSize.medium.name }?.text,
    var artistId: Long? = 0
)
