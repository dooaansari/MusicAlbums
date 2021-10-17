package com.app.musicalbums.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArtistResults(
    @SerializedName("opensearch:totalResults")
    @Expose
    var opensearchTotalResults: String,
    @SerializedName("opensearch:startIndex")
    @Expose
    var opensearchStartIndex: String,
    @SerializedName("opensearch:itemsPerPage")
    @Expose
    var opensearchItemsPerPage: String,
    @SerializedName("artistmatches")
    @Expose
    var artistmatches: ArtistMatches
)
