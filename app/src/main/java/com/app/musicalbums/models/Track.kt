package com.app.musicalbums.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "track",
    foreignKeys = [ForeignKey(
        entity = Album::class,
        parentColumns = ["name"],
        childColumns = ["albumName"],
        onDelete = CASCADE
    )]
)
@Parcelize
data class Track(

    @SerializedName("duration")
    @Expose
    var duration: Int? = 0,
    @SerializedName("url")
    @Expose
    var url: String? = null,

    @PrimaryKey
    @SerializedName("name")
    @Expose
    var name: String = "",

    var albumName: String = ""
) : Parcelable