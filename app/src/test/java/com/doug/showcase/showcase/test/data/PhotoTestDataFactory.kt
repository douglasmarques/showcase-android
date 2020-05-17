package com.doug.showcase.showcase.test.data

import com.doug.showcase.photo.network.model.Photo
import com.doug.showcase.photo.network.model.Photos
import com.doug.showcase.photo.network.model.SearchResponse
import com.doug.showcase.photo.repository.domain.PhotosResults
import com.doug.showcase.photo.repository.getImageUrl


object PhotoTestDataFactory {
    private const val ID = "49842360568"
    private const val OWNER = "12307620@N00"
    private const val SECRET = "61d8fd7487"
    private const val SERVER = "65535"
    private const val FARM = 66
    private const val TITLE = "Nice title"
    private const val PUBLIC = 1
    private const val FRIEND = 1
    private const val FAMILY = 1

    fun createTestSearchResponse(
        page: Int = 1,
        pages: Int = 1,
        perPage: Int = 100,
        total: Int = 13,
        photos: List<Photo> = listOf(createNetworkPhoto()),
        status: String = "OK"
    ) = SearchResponse(
        photos = Photos(
            page = page,
            pages = pages,
            perPage = perPage,
            total = total,
            photo = photos
        ),
        stat = status
    )

    private fun createNetworkPhoto(): Photo = Photo(
        id = ID,
        owner = OWNER,
        secret = SECRET,
        server = SERVER,
        farm = FARM,
        title = TITLE,
        isPublic = PUBLIC,
        isFriend = FRIEND,
        isFamily = FAMILY
    )

    fun createPhotosResults(
        photos: List<com.doug.showcase.photo.repository.domain.Photo> = listOf(createDomainPhoto()),
        totalPages: Int = 1
    ) = PhotosResults(
        photos = photos,
        totalPages = totalPages
    )

    fun createDomainPhoto() = createDomainPhoto(createNetworkPhoto())

    fun createDomainPhoto(networkPhoto: Photo) = com.doug.showcase.photo.repository.domain.Photo(
        url = networkPhoto.title,
        title = getImageUrl(networkPhoto)
    )
}
