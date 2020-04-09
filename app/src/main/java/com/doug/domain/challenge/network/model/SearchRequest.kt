package com.doug.domain.challenge.network.model

import com.squareup.moshi.Json

data class SearchRequest(
    @Json(name = "dwelling_types")
    val dwellingTypes: List<String>,
    @Json(name = "search_mode")
    val searchMode: String
)
