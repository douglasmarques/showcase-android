package com.doug.domain.challenge.repository

import com.doug.domain.challenge.network.DomainApi
import com.doug.domain.challenge.network.model.*
import com.doug.domain.challenge.repository.domain.DwellingType.*
import com.doug.domain.challenge.repository.domain.Property
import com.doug.domain.challenge.repository.domain.SearchType
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class PropertyRepositoryTest {

    lateinit var mockApi: DomainApi
    lateinit var repository: PropertyRepository

    @Test
    fun `search - API return result for search, repository should return a list of properties`() {
        val searchRequest = SearchRequest(
            dwellingTypes = listOf(APARTMENT.toString(), UNIT.toString(), FLAT.toString()),
            searchMode = SearchType.BUY.toString().toLowerCase(Locale.ROOT)
        )
        val testSearchResult = createTestSearchResult()
        mockApi = mock {
            onBlocking { search(searchRequest) } doReturn SearchResponse(
                resultsReturned = 1,
                resultsTotal = 1,
                searchResults = listOf(testSearchResult)
            )
        }
        repository = PropertyRepository(mockApi)
        runBlocking {
            assertEquals(listOf(createTestProperty()), repository.search(SearchType.BUY))
        }
    }

    @Test
    fun `search - API return no result for search, repository should return an empty list`() {
        val searchRequest = SearchRequest(
            dwellingTypes = listOf(APARTMENT.toString(), UNIT.toString(), FLAT.toString()),
            searchMode = SearchType.BUY.toString().toLowerCase(Locale.ROOT)
        )
        mockApi = mock {
            onBlocking { search(searchRequest) } doReturn SearchResponse(
                resultsReturned = 0,
                resultsTotal = 0,
                searchResults = emptyList()
            )
        }
        repository = PropertyRepository(mockApi)
        runBlocking {
            assertEquals(emptyList<Property>(), repository.search(SearchType.BUY))
        }
    }

    private fun createTestSearchResult(): SearchResult = SearchResult(
        advertiser = Advertiser(
            images = Images(
                logoUrl = "http://some.logo"
            )
        ),
        media = listOf(
            Medium(
                type = "photo",
                imageUrl = "http://image.url"
            )
        ),
        price = "$500.000",
        bathroomCount = 2f,
        bedroomCount = 3f,
        carspaceCount = 1
    )

    private fun createTestProperty(): Property = Property(
        image = "http://image.url",
        price = "$500.000",
        agencyLogo = "http://some.logo",
        baths = 2f,
        beds = 3f,
        carSpaces = 1
    )
}
