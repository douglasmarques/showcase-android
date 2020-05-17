package com.doug.showcase.photo.repository

import com.doug.showcase.photo.network.Api
import com.doug.showcase.photo.network.model.Photo
import com.doug.showcase.photo.network.model.SearchResponse
import com.doug.showcase.photo.repository.domain.PhotosResults

class PhotoRepository(
    private val api: Api
) {

    /**
     * Search for photos on the api and converts them to domain models.
     */
    suspend fun search(text: String, page: Int = 1): PhotosResults {
        val response = api.search(text = text, page = page)
        return createPhotoResults(response)
    }

    private fun createPhotoResults(response: SearchResponse) = PhotosResults(
        photos = response.photos?.photo?.map { photo ->
            com.doug.showcase.photo.repository.domain.Photo(
                url = getImageUrl(photo),
                title = photo.title
            )
        } ?: emptyList(),
        totalPages = response.photos?.pages ?: 0
    )
}

fun getImageUrl(photo: Photo): String =
    "https://farm${photo.farm}.static.flickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
