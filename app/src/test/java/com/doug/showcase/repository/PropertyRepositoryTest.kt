package com.doug.showcase.repository

import com.doug.showcase.network.Api
import com.doug.showcase.network.model.SearchRequest
import com.doug.showcase.network.model.SearchResponse
import com.doug.showcase.repository.domain.DwellingType.*
import com.doug.showcase.repository.domain.Property
import com.doug.showcase.repository.domain.SearchType
import com.doug.showcase.test.data.PropertyTestDataFactory.createTestProperty
import com.doug.showcase.test.data.PropertyTestDataFactory.createTestSearchResult
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

    lateinit var mockApi: Api
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
