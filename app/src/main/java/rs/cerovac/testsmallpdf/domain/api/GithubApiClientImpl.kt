package rs.cerovac.testsmallpdf.domain.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rs.cerovac.testsmallpdf.data.remote.api.GithubApi
import rs.cerovac.testsmallpdf.data.remote.api.base.Resource
import rs.cerovac.testsmallpdf.models.GithubCommitModel
import rs.cerovac.testsmallpdf.models.GithubRepoModel
import rs.cerovac.testsmallpdf.models.GithubUserModel

class GithubApiClientImpl(private val githubApi: GithubApi): GithubApiClient {

    override suspend fun getReposList(username: String, page: Int, pageSize: Int): Resource<List<GithubRepoModel>> = withContext(Dispatchers.IO) {
        try {
            val response = githubApi.getReposList(username,page, pageSize)
            if (response.isSuccessful) {
                Resource.success(response.body())

            } else {
                Resource.error(response.message())
            }
        } catch (ex: Throwable) {
            Resource.error("${ex.message}")
        }
   }

    override suspend fun getCommitsList(username: String, repoName:String, page: Int, pageSize: Int): Resource<List<GithubCommitModel>> = withContext(Dispatchers.IO) {
        try {
            val response = githubApi.getCommitsList(username, repoName, page, pageSize)
            if (response.isSuccessful) {
                Resource.success(response.body())

            } else {
                Resource.error(response.message())
            }
        } catch (ex: Throwable) {
            Resource.error("${ex.message}")
        }
    }

    override suspend fun getUserInfo(username: String): Resource<GithubUserModel> = withContext(Dispatchers.IO) {
        try {
            val response = githubApi.getUserInfo(username)
            if (response.isSuccessful) {
                Resource.success(response.body())
            } else {
                Resource.error(response.message())
            }
        } catch (ex: Throwable) {
            Resource.error("${ex.message}")
        }
    }
}