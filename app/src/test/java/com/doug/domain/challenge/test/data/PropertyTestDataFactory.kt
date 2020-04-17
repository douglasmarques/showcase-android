package com.doug.domain.challenge.test.data

import com.doug.domain.challenge.network.model.Advertiser
import com.doug.domain.challenge.network.model.Images
import com.doug.domain.challenge.network.model.Medium
import com.doug.domain.challenge.network.model.SearchResult
import com.doug.domain.challenge.repository.domain.Property

object PropertyTestDataFactory {
    private const val ID = 1
    private const val TYPE = "project"
    private const val IMAGE_URL = "http://image.url"
    private const val PRICE = "$500.000"
    private const val BEDS = 3f
    private const val BATHS = 2f
    private const val CAR_SPACE = 1
    private const val ADDRESS = "10 Bondi Road, Bondi, NSW"
    private const val AGENCY_LOGO_URL = "http://some.logo"
    private const val AGENCY_COLOR_HEX = "#ffffff"

    fun createTestSearchResult(): SearchResult = SearchResult(
        id = ID,
        dwellingType = TYPE,
        advertiser = Advertiser(
            images = Images(
                logoUrl = AGENCY_LOGO_URL
            ),
            preferredColorHex = AGENCY_COLOR_HEX
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
        carspaceCount = CAR_SPACE,
        address = ADDRESS
    )

    fun createTestProperty(): Property = Property(
        id = ID,
        type = TYPE,
        image = IMAGE_URL,
        price = PRICE,
        agencyLogo = AGENCY_LOGO_URL,
        agencyColor = AGENCY_COLOR_HEX,
        baths = BATHS.toInt(),
        beds = BEDS.toInt(),
        parking = 1,
        address = ADDRESS
    )
}
