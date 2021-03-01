package rs.cerovac.testsmallpdf.ui.commit_details

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import rs.cerovac.testsmallpdf.data.datasource.commits.CommitsListDataSource
import rs.cerovac.testsmallpdf.data.datasource.commits.CommitsListDataSourceFactory
import rs.cerovac.testsmallpdf.models.GithubCommitModel
import java.util.concurrent.Executors

class CommitDetailsViewModel(private val commitsListDataSourceFactory: CommitsListDataSourceFactory) : ViewModel() {

    lateinit var commitsLiveData: LiveData<PagedList<GithubCommitModel>>
    var dataSource: MutableLiveData<CommitsListDataSource>
    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()

    init {
        isWaiting.set(true)
        errorMessage.set(null)
        dataSource = commitsListDataSourceFactory.liveData
    }

    fun initCommitsListFactory(username: String, repoName: String) {
        commitsListDataSourceFactory.setParameters(username, repoName)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(CommitsListDataSource.PAGE_SIZE)
            .setPageSize(CommitsListDataSource.PAGE_SIZE)
            .setPrefetchDistance(3)
            .build()

        val executor = Executors.newFixedThreadPool(5)

        commitsLiveData = LivePagedListBuilder(commitsListDataSourceFactory, config)
            .setFetchExecutor(executor)
            .build()
    }

}