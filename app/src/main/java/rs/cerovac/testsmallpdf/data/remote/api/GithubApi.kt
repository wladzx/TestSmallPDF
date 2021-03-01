package rs.cerovac.testsmallpdf.data.remote.api

import retrofit2.Response
import retrofit2.http.*
import rs.cerovac.testsmallpdf.models.GithubCommitModel
import rs.cerovac.testsmallpdf.models.GithubRepoModel
import rs.cerovac.testsmallpdf.models.GithubUserModel

interface GithubApi {

    @GET("users/{username}/repos")
    suspend fun getReposList(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Response<List<GithubRepoModel>>

    @GET("repos/{username}/{repoName}/commits")
    suspend fun getCommitsList(
        @Path("username") username: String,
        @Path("repoName") repoName: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Response<List<GithubCommitModel>>

    @GET("users/{username}")
    suspend fun getUserInfo(
        @Path("username") username: String
    ): Response<GithubUserModel>
}