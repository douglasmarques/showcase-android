package com.doug.domain.challenge.test.data

import com.doug.domain.challenge.network.model.Advertiser
import com.doug.domain.challenge.network.model.Images
import com.doug.domain.challenge.network.model.Medium
import com.doug.domain.challenge.network.model.SearchResult
import com.doug.domain.challenge.repository.domain.Property

object PropertyTestDataFactory {
    const val LOGO_URL = "http://some.logo"
    const val IMAGE_URL = "http://image.url"
    const val PRICE = "$500.000"
    const val BEDS = 3f
    const val BATHS = 2f
    const val CAR_SPACE = 1

    fun createTestSearchResult(): SearchResult = SearchResult(
        advertiser = Advertiser(
            images = Images(
                logoUrl = LOGO_URL
            )
        ),
        media = listOf(
            Medium(
                type = "photo",
                imageUrl = IMAGE_URL
            )
        ),
        price = PRICE,
        bathroomCount = BATHS,
        bedroomCount = BEDS,
        carspaceCount = CAR_SPACE
    )

    fun createTestProperty(): Property = Property(
        image = IMAGE_URL,
        price = PRICE,
        agencyLogo = LOGO_URL,
        baths = BATHS,
        beds = BEDS,
        carSpaces = 1
    )
}
