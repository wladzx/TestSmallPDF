package rs.cerovac.testsmallpdf.ui.commit_details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import rs.cerovac.testsmallpdf.R
import rs.cerovac.testsmallpdf.models.GithubCommitModel

class CommitsListAdapter :
    PagedListAdapter<GithubCommitModel, CommitsListAdapter.UsersListViewHolder>(
        usersDiffCallback
    ) {

    private lateinit var context: Context

    inner class UsersListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoName: TextView = itemView.findViewById(R.id.tvRepoName)
        val repoDescription: TextView = itemView.findViewById(R.id.tvRepoDescription)
        val tvOpenIssues: TextView = itemView.findViewById(R.id.tvOpenIssues)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        val githubCommitModel = getItem(position)
        githubCommitModel?.let {
            holder.repoName.text = it.sha
            holder.repoDescription.text = it.nodeId
            holder.tvOpenIssues.text = it.url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.li_commit_details, parent, false)
        return UsersListViewHolder(view)
    }

    companion object {
        val usersDiffCallback = object : DiffUtil.ItemCallback<GithubCommitModel>() {
            override fun areItemsTheSame(
                oldItem: GithubCommitModel,
                newItem: GithubCommitModel
            ): Boolean {
                return oldItem.sha == newItem.sha
            }

            override fun areContentsTheSame(
                oldItem: GithubCommitModel,
                newItem: GithubCommitModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}