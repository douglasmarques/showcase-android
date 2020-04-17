package com.doug.domain.challenge.network.model

import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "results_returned")
    val resultsReturned: Int,
    @Json(name = "tracking")
    val tracking: Tracking? = null,
    @Json(name = "results_total")
    val resultsTotal: Int,
    @Json(name = "search_results")
    val searchResults: List<SearchResult>? = null
)

data class SearchResult(
    @Json(name = "id")
    val id: Int,
    @Json(name = "dwelling_type")
    val dwellingType: String?,
    @Json(name = "advertiser")
    val advertiser: Advertiser? = null,
    @Json(name = "price")
    val price: String? = null,
    @Json(name = "large_land")
    val largeLand: Boolean? = null,
    @Json(name = "land_area")
    val landArea: String? = null,
    @Json(name = "address")
    val address: String? = null,
    @Json(name = "bedroom_count")
    val bedroomCount: Float? = null,
    @Json(name = "listing_type")
    val listingType: String? = null,
    @Json(name = "homepass_enabled")
    val homepassEnabled: Boolean? = null,
    @Json(name = "promo_level")
    val promoLevel: String? = null,
    @Json(name = "bathroom_count")
    val bathroomCount: Float? = null,
    @Json(name = "media")
    val media: List<Medium>? = null,
    @Json(name = "has_video")
    val hasVideo: Boolean? = null,
    @Json(name = "headline")
    val headline: String? = null,
    @Json(name = "carspace_count")
    val carspaceCount: Int? = null,
    @Json(name = "geo_location")
    val geoLocation: GeoLocation? = null,
    @Json(name = "topspot")
    val topspot: Topspot? = null,
    @Json(name = "metadata")
    val metadata: Metadata? = null,
    @Json(name = "project")
    val project: Project? = null,
    @Json(name = "lifecycle_status")
    val lifecycleStatus: String? = null,
    @Json(name = "additional_features")
    val additionalFeatures: List<String>? = null
)

data class Tracking(
    @Json(name = "search.surSuburbs")
    val searchSurSuburbs: Boolean? = null,
    @Json(name = "search.excludeUnderOffer")
    val searchExcludeUnderOffer: Boolean? = null,
    @Json(name = "search.searchResultCount")
    val searchSearchResultCount: Int? = null,
    @Json(name = "cat.subCategory1")
    val catSubCategory1: String? = null,
    @Json(name = "search.propertyType")
    val searchPropertyType: String? = null,
    @Json(name = "cat.primaryCategory")
    val catPrimaryCategory: String? = null
)

data class Advertiser(
    @Json(name = "images")
    val images: Images? = null,
    @Json(name = "preferred_color_hex")
    val preferredColorHex: String? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "agency_listing_contacts")
    val agencyListingContacts: List<AgencyListingContact>? = null
)

data class AddressComponents(
    @Json(name = "street_number")
    val streetNumber: String? = null,
    @Json(name = "suburb")
    val suburb: String? = null,
    @Json(name = "street")
    val street: String? = null,
    @Json(name = "state_short")
    val stateShort: String? = null,
    @Json(name = "suburb_id")
    val suburbId: Int? = null,
    @Json(name = "postcode")
    val postcode: String? = null
)

data class Images (
    @Json(name = "logo_url")
    val logoUrl: String? = null
)

data class AgencyListingContact(
    @Json(name = "display_full_name")
    val displayFullName: String? = null,
    @Json(name = "image_url")
    val imageUrl: String? = null
)

data class Medium(
    @Json(name = "image_url")
    val imageUrl: String? = null,
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "media_type")
    val mediaType: String? = null
)

data class GeoLocation(
    @Json(name = "latitude")
    val latitude: Float? = null,
    @Json(name = "longitude")
    val longitude: Float? = null
)

data class Topspot (
    @Json(name = "available_listings")
    val availableListings: Int? = null
)

data class Metadata (
    @Json(name = "address_components")
    val addressComponents: AddressComponents? = null
)

data class Project(
    @Json(name = "child_listings")
    val childListings: List<ChildListing>? = null,
    @Json(name = "project_color_hex")
    val projectColorHex: String? = null,
    @Json(name = "project_name")
    val projectName: String? = null,
    @Json(name = "project_banner_image")
    val projectBannerImage: String? = null,
    @Json(name = "project_logo_image")
    val projectLogoImage: String? = null
)

data class ChildListing(
    @Json(name = "bathroom_count")
    val bathroomCount: Float? = null,
    @Json(name = "price")
    val price: String? = null,
    @Json(name = "carspace_count")
    val carspaceCount: Int? = null,
    @Json(name = "bedroom_count")
    val bedroomCount: Float? = null,
    @Json(name = "lifecycle_status")
    val lifecycleStatus: String? = null,
    @Json(name = "homepass_enabled")
    val homepassEnabled: Boolean? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "property_size")
    val propertySize: String? = null
)

