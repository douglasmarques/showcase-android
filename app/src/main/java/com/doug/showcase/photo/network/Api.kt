package com.doug.showcase.photo.network

import com.doug.showcase.photo.network.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    companion object {
        const val API_KEY = "96358825614a5d3b1a1c3fd87fca2b47"
        const val URL = "https://api.flickr.com/services/rest/"
    }

    @GET("?method=flickr.photos.search&safe_search=1&nojsoncallback=1&format=json")
    suspend fun search(
        @Query("text") text: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): SearchResponse

}

