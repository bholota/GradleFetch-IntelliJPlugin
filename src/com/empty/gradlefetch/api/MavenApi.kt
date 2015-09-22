package com.empty.gradlefetch.api

import com.empty.gradlefetch.model.response.MavenResponse
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

/**
 * Created by mr3mpty on 21.09.2015.
 */
public interface MavenApi {

    @GET("/solrsearch/select?wt=json")
    public fun searchRepository(@Query("q") searchForText: String, @Query("rows") rows: Int?): Call<MavenResponse>

    companion object {
        public val BASE_URL: String = "https://search.maven.org"
    }
}
