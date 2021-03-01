package rs.cerovac.testsmallpdf.ui.user_repos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import rs.cerovac.testsmallpdf.R
import rs.cerovac.testsmallpdf.models.GithubRepoModel

class UserReposListAdapter(private val listener: ReposListAdapterInteraction) :
    PagedListAdapter<GithubRepoModel, UserReposListAdapter.UsersListViewHolder>(
        usersDiffCallback
    ) {

    private lateinit var context: Context

    interface ReposListAdapterInteraction {
        fun onRepoItemClick(githubRepoModel: GithubRepoModel)
    }

    inner class UsersListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoItem: ConstraintLayout = itemView.findViewById(R.id.repoItem)
        val repoName: TextView = itemView.findViewById(R.id.tvRepoName)
        val repoDescription: TextView = itemView.findViewById(R.id.tvRepoDescription)
        val tvOpenIssues: TextView = itemView.findViewById(R.id.tvOpenIssues)
        val tvOwner: TextView = itemView.findViewById(R.id.tvOwner)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        val githubRepoModel = getItem(position)
        githubRepoModel?.let {
            holder.repoName.text = it.name
            holder.repoDescription.text = it.description
            holder.tvOpenIssues.text = context.resources.getString(R.string.open_issues_count, it.openIssues.toString())
            holder.tvOwner.text = context.resources.getString(R.string.owner, it.owner.login)

            holder.repoItem.setOnClickListener {
                listener.onRepoItemClick(githubRepoModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.li_user_repo, parent, false)
        return UsersListViewHolder(view)
    }

    companion object {
        val usersDiffCallback = object : DiffUtil.ItemCallback<GithubRepoModel>() {
            override fun areItemsTheSame(
                oldItem: GithubRepoModel,
                newItem: GithubRepoModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GithubRepoModel,
                newItem: GithubRepoModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}