package com.empty.gradlefetch.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by mr3mpty on 21.09.2015.
 */
public class MavenHeader {
    public var status: Int? = null
    public var QTime: Long? = null
    public var params: Params? = null

    public class Params {
        public var spellcheck: String? = null
        public var fl: String? = null
        public var sort: String? = null
        public var ident: String? = null
        public var q: String? = null
        public var qf: String? = null

        @SerializedName("spellcheck.count")
        public var spellcheckCount: String? = null
        public var wt: String? = null
        public var rows: String? = null
        public var version: String? = null
        public var defType: String? = null
    }
}
