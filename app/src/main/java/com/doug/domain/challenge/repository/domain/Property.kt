package com.doug.domain.challenge.repository.domain

data class Property(
    val image: String,
    val price: String,
    val beds: Int,
    val baths: Int,
    val carSpaces: Int,
    val agencyLogo: String,
    val address: String
)
