package com.android.network.di

import com.android.network.utils.Constants.BASE_URL
import com.android.network.webservice.IApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    //provide Gson instance
    @Provides
    @Singleton
    fun getGson(): Gson {
        return GsonBuilder().serializeNulls().setLenient().create()
    }

    //provide Inteceptor
    @Provides
    @Singleton
    fun getInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url
            val url = originalHttpUrl.newBuilder().build()
            //.addQueryParameter(APIKEY, BuildConfig.API_KEY)
            //.addQueryParameter(HASH, BuildConfig.HASH)
            //.addQueryParameter(TIMESTAMP, TIMESTAMP_VALUE)
            //.addQueryParameter(LIMIT, LIMIT_VALUE).build()
            val requestBuilder = originalRequest.newBuilder().url(url)
            val actualRequest = requestBuilder.build()
            chain.proceed(actualRequest)
        }
    }

    //Provide okHttpClient
    @Provides
    @Singleton
    fun getOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
        return httpBuilder
            .protocols(mutableListOf(Protocol.HTTP_1_1))
            .build()
    }

    //Provide retrofit instance
    @Provides
    @Singleton
    fun providesRetrofitInstance(
        client: OkHttpClient,
        gson: Gson
    ): IApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(IApiService::class.java)
    }
}