package rs.cerovac.testsmallpdf.data.datasource.repos

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import rs.cerovac.testsmallpdf.models.GithubRepoModel
import rs.cerovac.testsmallpdf.domain.api.GithubApiClient

class ReposListDataSourceFactory(private val githubApiClient: GithubApiClient): DataSource.Factory<Int, GithubRepoModel>() {

    val liveData: MutableLiveData<ReposListDataSource> = MutableLiveData()
    private lateinit var _username: String

    override fun create(): DataSource<Int, GithubRepoModel> {
        val reposListDataSource = ReposListDataSource(_username, githubApiClient)
        liveData.postValue(reposListDataSource)
        return reposListDataSource
    }

    fun setUsername(username: String){
        _username = username
    }

}