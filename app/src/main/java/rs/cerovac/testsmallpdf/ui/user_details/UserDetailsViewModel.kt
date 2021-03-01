package rs.cerovac.testsmallpdf.ui.user_details

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.cerovac.testsmallpdf.data.remote.api.base.Status
import rs.cerovac.testsmallpdf.models.GithubUserModel
import rs.cerovac.testsmallpdf.domain.api.GithubApiClient
import rs.cerovac.testsmallpdf.utils.SingleLiveEvent

class UserDetailsViewModel(private val githubApiClient: GithubApiClient) : ViewModel() {

    private val isWaiting: ObservableField<Boolean> = ObservableField()
    private val errorMessage: ObservableField<String> = ObservableField()
    val githubUserModel: ObservableField<GithubUserModel> = ObservableField()

    private val _onReposClicked = SingleLiveEvent<Unit>()
    val onReposClicked: LiveData<Unit>
        get() = _onReposClicked

    init {
        isWaiting.set(true)
        errorMessage.set(null)
    }

    fun repoButtonClicked() {
        _onReposClicked.call()
    }

    fun getUserInfoByUsername(username: String) {
        viewModelScope.launch {
            val result = githubApiClient.getUserInfo(username)
            if (result.status == Status.SUCCESS) {
                githubUserModel.set(result.data)
                errorMessage.set(null)
            } else {
                githubUserModel.set(null)
                errorMessage.set(result.message)
            }
            isWaiting.set(false)
        }
    }

}