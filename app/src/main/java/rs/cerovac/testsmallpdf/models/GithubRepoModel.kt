package rs.cerovac.testsmallpdf.models

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class GithubRepoModel(
    @SerializedName("id") var id: Long,
    @SerializedName("open_issues") var openIssues: Int,
    @SerializedName("name") var name: String,
    @SerializedName("full_name") var fullName: String,
    @SerializedName("private") var private: Boolean,
    @SerializedName("owner") var owner: GithubUserModel,
    @SerializedName("description") var description: String,
    @SerializedName("fork") var fork: Boolean,
    @SerializedName("url") var url: String,
    @SerializedName("gists_url") var gistsUrl: String,
    @SerializedName("created_at") var createdAt: Date,
    @SerializedName("updated_at") var updatedAt: Date
) {
   @SuppressLint("SimpleDateFormat")
   fun getCreatedDateAsString(): String? {
        val formatter = SimpleDateFormat("dd MMMM yyyy, hh:mm:ss");
        return formatter.format(createdAt)
    }

    @SuppressLint("SimpleDateFormat")
    fun getUpdatedDateAsString(): String? {
        val formatter = SimpleDateFormat("dd MMMM yyyy, hh:mm:ss");
        return formatter.format(updatedAt)
    }
}