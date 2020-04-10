package com.doug.domain.challenge.repository.domain

data class Property(
    val image: String,
    val price: String,
    val beds: Float,
    val baths: Float,
    val carSpaces: Int,
    val agencyLogo: String
)
