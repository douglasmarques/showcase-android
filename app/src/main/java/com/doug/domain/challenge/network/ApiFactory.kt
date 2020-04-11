package com.doug.domain.challenge.network

import com.doug.domain.challenge.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiFactory {

    val domainApi: DomainApi
    private var client: OkHttpClient
    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    init {
        client = OkHttpClient().newBuilder()
            .addInterceptor(getLoggingInterceptor())
            .build()

        domainApi = createRetrofit().create(DomainApi::class.java)
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    private fun createRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(DomainApi.URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()

}

