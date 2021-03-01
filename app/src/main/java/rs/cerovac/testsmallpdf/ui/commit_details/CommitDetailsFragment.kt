package rs.cerovac.testsmallpdf.ui.commit_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.cerovac.testsmallpdf.R
import rs.cerovac.testsmallpdf.data.remote.api.base.Status
import rs.cerovac.testsmallpdf.databinding.CommitDetailsFragmentBinding

class CommitDetailsFragment : Fragment() {

    private val viewModel: CommitDetailsViewModel by viewModel()
    private lateinit var rvCommits: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: CommitDetailsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.commit_details_fragment, container, false)

        binding.vm = viewModel
        rvCommits = binding.root.findViewById(R.id.rvCommits)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val username: String = CommitDetailsFragmentArgs.fromBundle(it) .username
            val repoName: String = CommitDetailsFragmentArgs.fromBundle(it) .repoName
            viewModel.initCommitsListFactory(username, repoName)
        }
        initAdapterAndObserve()
    }

    private fun initAdapterAndObserve() {
        val commitsListAdapter = CommitsListAdapter()

        rvCommits.layoutManager = LinearLayoutManager(context)
        rvCommits.adapter = commitsListAdapter

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

        viewModel.commitsLiveData.observe(viewLifecycleOwner, {
            commitsListAdapter.submitList(it)
        })
    }
}