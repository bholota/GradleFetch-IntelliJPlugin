package com.empty.gradlefetch.api

import com.empty.gradlefetch.util.GsonConverterFactory
import com.squareup.okhttp.OkHttpClient
import retrofit.Retrofit

/**
 * Created by mr3mpty on 21.09.2015.
 */
public class MavenApiClient {

    public val api: MavenApi

    init {
        val builder = Retrofit.Builder()
                .baseUrl(MavenApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient())

        api = builder.build().create(MavenApi::class.java)
    }
}
