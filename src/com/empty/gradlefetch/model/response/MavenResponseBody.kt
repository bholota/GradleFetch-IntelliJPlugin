package com.empty.gradlefetch.model.response

import java.util.ArrayList

/**
 * Created by mr3mpty on 21.09.2015.
 */
public class MavenResponseBody {
    public var numFound: Int? = null
    public var start: Int? = null
    public var docs: ArrayList<MavenItem>? = null

    public class MavenItem {
        public var id: String? = null
        public var g: String? = null
        public var a: String? = null
        public var latestVersion: String? = null
        public var repositoryId: String? = null
        public var p: String? = null
        public var timestamp: Long? = null
        public var versionCount: Int? = null
    }
}
