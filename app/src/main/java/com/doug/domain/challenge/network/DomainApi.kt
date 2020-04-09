package com.doug.domain.challenge.network

import com.doug.domain.challenge.network.model.SearchRequest
import com.doug.domain.challenge.network.model.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.POST

interface DomainApi {

    companion object {
        const val URL = "https://domain-adapter-api.domain.com.au/v1/"
    }

    @POST("search")
    fun search(request: SearchRequest): Deferred<SearchResponse>

}

