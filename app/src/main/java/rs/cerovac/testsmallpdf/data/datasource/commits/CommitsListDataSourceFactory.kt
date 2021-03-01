package rs.cerovac.testsmallpdf.data.datasource.commits

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import rs.cerovac.testsmallpdf.domain.api.GithubApiClient
import rs.cerovac.testsmallpdf.models.GithubCommitModel

class CommitsListDataSourceFactory(private val githubApiClient: GithubApiClient) :
    DataSource.Factory<Int, GithubCommitModel>() {

    val liveData: MutableLiveData<CommitsListDataSource> = MutableLiveData()
    private lateinit var _username: String
    private lateinit var _repoName: String

    override fun create(): DataSource<Int, GithubCommitModel> {
        val commitsListDataSource = CommitsListDataSource(_username, _repoName, githubApiClient)
        liveData.postValue(commitsListDataSource)
        return commitsListDataSource
    }

    fun setParameters(username: String, repoName: String) {
        _username = username
        _repoName = repoName
    }

}