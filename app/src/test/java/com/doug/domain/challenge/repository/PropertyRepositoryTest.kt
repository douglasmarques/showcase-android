package com.doug.domain.challenge.repository

import com.doug.domain.challenge.network.DomainApi
import com.doug.domain.challenge.network.model.SearchRequest
import com.doug.domain.challenge.network.model.SearchResponse
import com.doug.domain.challenge.repository.domain.DwellingType.*
import com.doug.domain.challenge.repository.domain.Property
import com.doug.domain.challenge.repository.domain.SearchType
import com.doug.domain.challenge.test.data.PropertyTestDataFactory.createTestProperty
import com.doug.domain.challenge.test.data.PropertyTestDataFactory.createTestSearchResult
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
            val propertyList = repository.search(SearchType.BUY)
            assertEquals(listOf(createTestProperty()), propertyList)
            assertEquals(1, propertyList.size)
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
            val propertyList = repository.search(SearchType.BUY)
            assertEquals(emptyList<Property>(), propertyList)
            assertEquals(true, propertyList.isEmpty())
        }
    }
}
