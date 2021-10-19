package com.app.musicalbums.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArtistResults(
    @SerializedName("opensearch:totalResults")
    @Expose
    var opensearchTotalResults: Int,
    @SerializedName("opensearch:startIndex")
    @Expose
    var opensearchStartIndex: Int,
    @SerializedName("opensearch:itemsPerPage")
    @Expose
    var opensearchItemsPerPage: Int,
    @SerializedName("artistmatches")
    @Expose
    var artistmatches: ArtistMatches
)
