package rs.cerovac.testsmallpdf.models

import com.google.gson.annotations.SerializedName

data class GithubCommitModel(
    @SerializedName("sha") var sha: String,
    @SerializedName("node_id") var nodeId: String,
    @SerializedName("url") var url: String,
)
