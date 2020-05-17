package com.doug.showcase.repository

import com.doug.showcase.photo.network.Api
import com.doug.showcase.photo.repository.PhotoRepository
import com.doug.showcase.showcase.test.data.PhotoTestDataFactory.createTestSearchResponse
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhotoRepositoryTest {

    lateinit var mockApi: Api
    lateinit var repository: PhotoRepository

    @Test
    fun `search - API return result for search, repository should return a list of photos`() {
        val testSearchResponse = createTestSearchResponse()
        mockApi = mock {
            onBlocking { search("explore") } doReturn testSearchResponse
        }
        repository = PhotoRepository(mockApi)
        runBlocking {
            val photosResults = repository.search("explore")
            assertEquals(testSearchResponse.photos?.photo?.size, photosResults.photos.size)
            assertEquals(testSearchResponse.photos?.pages, photosResults.totalPages)
        }
    }

    @Test
    fun `search - API return no result for search, repository should return an empty list`() {
        val testSearchResponse = createTestSearchResponse(
            pages = 0,
            total = 0,
            perPage = 0,
            photos = emptyList()
        )
        mockApi = mock {
            onBlocking { search("no results") } doReturn testSearchResponse
        }
        repository = PhotoRepository(mockApi)
        runBlocking {
            val photosResults = repository.search("no results")
            assertEquals(0, photosResults.photos.size)
            assertEquals(0, photosResults.totalPages)
        }
    }
}
