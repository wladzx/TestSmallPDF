package rs.cerovac.testsmallpdf.domain.api

import rs.cerovac.testsmallpdf.data.remote.api.base.Resource
import rs.cerovac.testsmallpdf.models.GithubCommitModel
import rs.cerovac.testsmallpdf.models.GithubRepoModel
import rs.cerovac.testsmallpdf.models.GithubUserModel


interface GithubApiClient {

    suspend fun getReposList(username: String, page: Int, pageSize: Int): Resource<List<GithubRepoModel>>

    suspend fun getCommitsList(username: String, repoName: String, page: Int, pageSize: Int): Resource<List<GithubCommitModel>>

    suspend fun getUserInfo(username: String): Resource<GithubUserModel>
}