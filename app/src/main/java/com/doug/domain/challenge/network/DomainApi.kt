package com.doug.domain.challenge.network

import com.doug.domain.challenge.network.model.SearchRequest
import com.doug.domain.challenge.network.model.SearchResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DomainApi {

    companion object {
        const val URL = "https://domain-adapter-api.domain.com.au/v1/"
    }

    @POST("search")
    suspend fun search(@Body request: SearchRequest): SearchResponse

}

