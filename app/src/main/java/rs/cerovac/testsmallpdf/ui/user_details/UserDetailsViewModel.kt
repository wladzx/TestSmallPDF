package rs.cerovac.testsmallpdf.ui.user_details

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.cerovac.testsmallpdf.data.remote.api.base.Resource
import rs.cerovac.testsmallpdf.data.remote.api.base.Status
import rs.cerovac.testsmallpdf.models.GithubUserModel
import rs.cerovac.testsmallpdf.domain.api.GithubApiClient
import rs.cerovac.testsmallpdf.utils.SingleLiveEvent

class UserDetailsViewModel(private val githubApiClient: GithubApiClient) : ViewModel() {

    val githubUserModel: ObservableField<GithubUserModel> = ObservableField()

    private val _result: SingleLiveEvent<Resource<Unit>> = SingleLiveEvent()
    val result: LiveData<Resource<Unit>>
        get() = _result

    private val _onReposClicked = SingleLiveEvent<Unit>()
    val onReposClicked: LiveData<Unit>
        get() = _onReposClicked

    fun repoButtonClicked() {
        _onReposClicked.call()
    }

    fun getUserInfoByUsername(username: String) {
        _result.value = Resource.loading()
        viewModelScope.launch {
            val result = githubApiClient.getUserInfo(username)
            if (result.status == Status.SUCCESS) {
                _result.value = Resource.success()
                githubUserModel.set(result.data)
            } else {
                _result.value = result.message?.let { Resource.error(it) }
                githubUserModel.set(null)
            }
        }
    }
}