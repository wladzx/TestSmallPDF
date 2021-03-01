package rs.cerovac.testsmallpdf.models

import com.google.gson.annotations.SerializedName

data class GithubCommitModel(
    @SerializedName("sha") var sha: String,
    @SerializedName("node_id") var nodeId: String,
    @SerializedName("url") var url: String,
    @SerializedName("author") var author: GithubUserModel,
    @SerializedName("commiter") var commiter: GithubUserModel,
    @SerializedName("commit") var commit: GithubCommitModel,
    @SerializedName("message") var message: String
)

