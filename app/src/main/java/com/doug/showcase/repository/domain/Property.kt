package com.doug.showcase.repository.domain

data class Property(
    val id: Int,
    val type: String?,
    val image: String,
    val price: String,
    val beds: Int,
    val baths: Int,
    val parking: Int,
    val agencyLogo: String,
    val agencyColor: String,
    val address: String
)
