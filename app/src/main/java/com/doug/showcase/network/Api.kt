package com.doug.showcase.network

import com.doug.showcase.network.model.SearchRequest
import com.doug.showcase.network.model.SearchResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    companion object {
        const val URL = "https://domain-adapter-api.domain.com.au/v1/"
    }

    @POST("search")
    suspend fun search(@Body request: SearchRequest): SearchResponse

}

