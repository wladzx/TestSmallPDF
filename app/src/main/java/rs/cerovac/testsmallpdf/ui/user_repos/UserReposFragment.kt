package rs.cerovac.testsmallpdf.ui.user_repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.cerovac.testsmallpdf.R
import rs.cerovac.testsmallpdf.data.remote.api.base.Status
import rs.cerovac.testsmallpdf.databinding.UserReposFragmentBinding
import rs.cerovac.testsmallpdf.models.GithubRepoModel
import rs.cerovac.testsmallpdf.ui.user_details.UserDetailsFragmentArgs

class UserReposFragment : Fragment(), UserReposListAdapter.ReposListAdapterInteraction {

    private val viewModel: UserReposViewModel by viewModel()
    private lateinit var rvRepos: RecyclerView
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: UserReposFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.user_repos_fragment, container, false)

        binding.vm = viewModel
        rvRepos = binding.root.findViewById(R.id.rvRepos)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            username = UserDetailsFragmentArgs.fromBundle(it).username
            viewModel.initReposListFactory(username)
        }
        initAdapterAndObserve()
    }

    private fun initAdapterAndObserve() {
        val usersListAdapter = UserReposListAdapter(this)

        rvRepos.layoutManager = LinearLayoutManager(context)
        rvRepos.adapter = usersListAdapter

        Transformations.switchMap(viewModel.dataSource) { dataSource -> dataSource.loadStateLiveData }
            .observe(viewLifecycleOwner, {
                when (it) {
                    Status.LOADING -> {
                        viewModel.isWaiting.set(true)
                        viewModel.errorMessage.set(null)
                    }
                    Status.SUCCESS -> {
                        viewModel.isWaiting.set(false)
                        viewModel.errorMessage.set(null)
                    }
                    Status.EMPTY -> {
                        viewModel.isWaiting.set(false)
                        viewModel.errorMessage.set(getString(R.string.msg_repos_list_is_empty))
                    }
                    else -> {
                        viewModel.isWaiting.set(false)
                        viewModel.errorMessage.set(getString(R.string.msg_fetch_repos_list_error))
                    }
                }
            })

        viewModel.reposLiveData.observe(viewLifecycleOwner, {
            usersListAdapter.submitList(it)
        })
    }

    override fun onRepoItemClick(githubRepoModel: GithubRepoModel) {
        findNavController().navigate(
            UserReposFragmentDirections.actionNavigationUserReposToNavigationCommitDetails(username, githubRepoModel.name)
        )
    }
}