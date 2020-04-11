package com.doug.domain.challenge.repository

import com.doug.domain.challenge.network.ApiFactory
import com.doug.domain.challenge.network.DomainApi
import com.doug.domain.challenge.network.model.Medium
import com.doug.domain.challenge.network.model.SearchRequest
import com.doug.domain.challenge.network.model.SearchResult
import com.doug.domain.challenge.repository.domain.DwellingType.*
import com.doug.domain.challenge.repository.domain.Property
import com.doug.domain.challenge.repository.domain.SearchType
import java.util.*

class PropertyRepository(
    private val domainApi: DomainApi = ApiFactory().domainApi
) {

    /**
     * Search for properties on the api and converts them domain models.
     */
    suspend fun search(type: SearchType): List<Property> {
        val searchRequest = SearchRequest(
            dwellingTypes = listOf(APARTMENT.toString(), UNIT.toString(), FLAT.toString()),
            searchMode = type.toString().toLowerCase(Locale.ROOT)
        )
        val response = domainApi.search(searchRequest)
        return if (response.resultsReturned > 0) {
            response.searchResults?.map { result ->
                createProperty(result)
            } ?: emptyList()
        } else {
            emptyList()
        }
    }

    private fun createProperty(result: SearchResult) = Property(
        image = getPropertyImage(result.media),
        price = result.price ?: "",
        beds = result.bedroomCount?.toInt() ?: 0,
        baths = result.bathroomCount?.toInt() ?: 0,
        carSpaces = result.carspaceCount ?: 0,
        agencyLogo = result.advertiser?.images?.logoUrl ?: "",
        address = result.address ?: ""
    )

    private fun getPropertyImage(media: List<Medium>?): String {
        return media?.firstOrNull { medium ->
            medium.type == "photo" && !medium.imageUrl.isNullOrBlank()
        }?.imageUrl ?: ""
    }
}
