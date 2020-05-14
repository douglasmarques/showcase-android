package com.doug.showcase.di

import com.doug.showcase.BuildConfig
import com.doug.showcase.network.Api
import com.doug.showcase.repository.PropertyRepository
import com.doug.showcase.ui.properties.PropertiesViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Koin modules responsible for creating the dependencies and inject where necessary.
 */

val viewModelModule = module {
    viewModel {
        PropertiesViewModel(get())
    }
}

val repositoryModule = module {
    single {
        PropertyRepository(get())
    }
}

val apiModule = module {
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
    single { provideApi(get()) }
}

val retrofitModule = module {

    fun provideMoshi(): Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    fun provideHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(interceptor)
        .build()

    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(Api.URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
        return builder.build()
    }

    single { provideMoshi() }
    single { provideHttpLoggingInterceptor() }
    single { provideHttpClient(get()) }
    single { provideRetrofit(get(), get()) }
}
