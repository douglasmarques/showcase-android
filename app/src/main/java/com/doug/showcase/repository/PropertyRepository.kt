package com.doug.showcase.repository

import com.doug.showcase.network.Api
import com.doug.showcase.network.model.Medium
import com.doug.showcase.network.model.SearchRequest
import com.doug.showcase.network.model.SearchResult
import com.doug.showcase.repository.domain.DwellingType.*
import com.doug.showcase.repository.domain.Property
import com.doug.showcase.repository.domain.SearchType
import java.util.*

class PropertyRepository(
    private val api: Api
) {

    /**
     * Search for properties on the api and converts them to domain models.
     */
    suspend fun search(type: SearchType): List<Property> {
        val searchRequest = SearchRequest(
            dwellingTypes = listOf(APARTMENT.toString(), UNIT.toString(), FLAT.toString()),
            searchMode = type.toString().toLowerCase(Locale.ROOT)
        )
        val response = api.search(searchRequest)
        return response.searchResults?.map { result ->
            createProperty(result)
        } ?: emptyList()
    }

    private fun createProperty(result: SearchResult) = Property(
        id = result.id,
        type = result.dwellingType,
        image = getPropertyImage(result.media),
        price = result.price ?: "",
        beds = result.bedroomCount?.toInt() ?: 0,
        baths = result.bathroomCount?.toInt() ?: 0,
        parking = result.carspaceCount ?: 0,
        agencyLogo = result.advertiser?.images?.logoUrl ?: "",
        agencyColor = result.advertiser?.preferredColorHex ?: "",
        address = result.address ?: ""
    )

    private fun getPropertyImage(media: List<Medium>?): String {
        return media?.firstOrNull { medium ->
            medium.type == "photo" && !medium.imageUrl.isNullOrBlank()
        }?.imageUrl ?: ""
    }
}
