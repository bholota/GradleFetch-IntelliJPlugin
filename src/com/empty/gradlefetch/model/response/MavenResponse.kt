package com.empty.gradlefetch.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by mr3mpty on 21.09.2015.
 */
public class MavenResponse {
    SerializedName("responseHeader")
    public var header: MavenHeader? = null
    public var response: MavenResponseBody? = null
}
