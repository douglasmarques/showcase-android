package com.doug.showcase.photo.repository.domain


data class PhotosResults(
    val photos: List<Photo>,
    val totalPages: Int
)

data class Photo(
    val url: String,
    val title: String
)

