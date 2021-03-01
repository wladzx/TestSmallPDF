package rs.cerovac.testsmallpdf.data.datasource.commits

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*
import androidx.lifecycle.MutableLiveData
import rs.cerovac.testsmallpdf.data.remote.api.base.Status
import rs.cerovac.testsmallpdf.domain.api.GithubApiClient
import rs.cerovac.testsmallpdf.models.GithubCommitModel

class CommitsListDataSource(
    private val username: String,
    private val repoName: String,
    private val githubApiClient: GithubApiClient
) : PageKeyedDataSource<Int, GithubCommitModel>() {

    private val dataSourceJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + dataSourceJob)
    val loadStateLiveData: MutableLiveData<Status> = MutableLiveData()

    companion object {
        const val PAGE_SIZE = 15
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GithubCommitModel>
    ) {
        scope.launch {
            loadStateLiveData.postValue(Status.LOADING)
            val response = githubApiClient.getCommitsList(username, repoName, 1, PAGE_SIZE)
            when (response.status) {
                Status.ERROR -> loadStateLiveData.postValue(Status.ERROR)
                Status.EMPTY -> loadStateLiveData.postValue(Status.EMPTY)
                else -> {
                    response.data?.let {
                        callback.onResult(it, null, 2)
                        loadStateLiveData.postValue(Status.SUCCESS)
                    }
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GithubCommitModel>) {
        scope.launch {
            val response = githubApiClient.getCommitsList(username, repoName, params.key, PAGE_SIZE)
            response.data?.let {
                callback.onResult(it, params.key + 1)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubCommitModel>) {

    }
}