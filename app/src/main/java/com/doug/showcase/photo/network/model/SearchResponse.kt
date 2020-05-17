package com.doug.showcase.photo.network.model

import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "photos")
    val photos: Photos?,
    @Json(name = "stat")
    val stat: String
)

data class Photos(
    @Json(name = "photo")
    val photo: List<Photo>? = null,
    @Json(name = "page")
    val page: Int,
    @Json(name = "pages")
    val pages: Int,
    @Json(name = "perpage")
    val perPage: Int,
    @Json(name = "total")
    val total: Int
)

data class Photo(
    @Json(name = "id")
    val id: String,
    @Json(name = "owner")
    val owner: String,
    @Json(name = "secret")
    val secret: String,
    @Json(name = "server")
    val server: String,
    @Json(name = "farm")
    val farm: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "ispublic")
    val isPublic: Int,
    @Json(name = "isfriend")
    val isFriend: Int,
    @Json(name = "isfamily")
    val isFamily: Int
)

