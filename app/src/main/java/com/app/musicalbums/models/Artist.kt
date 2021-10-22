package com.app.musicalbums.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.app.musicalbums.enums.ImageSize
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "artist")
data class Artist(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @Ignore
    @SerializedName("listeners")
    @Expose
    var listeners: String? = null,

    @SerializedName("mbid")
    @Expose
    var mbid: String? = null,

    @Ignore
    @SerializedName("url")
    @Expose
    var url: String? = null,

    @Ignore
    @SerializedName("streamable")
    @Expose
    var streamable: String? = null,

    @SerializedName("image")
    @Expose
    var images: List<Image>? = null,

    val image: String? = images?.find { it.size == ImageSize.medium.name }?.text,
)