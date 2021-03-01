package rs.cerovac.testsmallpdf.ui.user_repos

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import rs.cerovac.testsmallpdf.data.datasource.repos.ReposListDataSource
import rs.cerovac.testsmallpdf.data.datasource.repos.ReposListDataSourceFactory
import rs.cerovac.testsmallpdf.models.GithubRepoModel
import java.util.concurrent.Executors

class UserReposViewModel(private val reposListDataSourceFactory: ReposListDataSourceFactory) : ViewModel() {

    lateinit var reposLiveData: LiveData<PagedList<GithubRepoModel>>
    var dataSource: MutableLiveData<ReposListDataSource>
    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()

    init {
        isWaiting.set(true)
        errorMessage.set(null)
        dataSource = reposListDataSourceFactory.liveData
    }

     fun initReposListFactory(username: String) {
        reposListDataSourceFactory.setUsername(username)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(ReposListDataSource.PAGE_SIZE)
            .setPageSize(ReposListDataSource.PAGE_SIZE)
            .setPrefetchDistance(3)
            .build()

        val executor = Executors.newFixedThreadPool(5)

        reposLiveData = LivePagedListBuilder(reposListDataSourceFactory, config)
            .setFetchExecutor(executor)
            .build()
    }

}