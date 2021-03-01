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
        val sha: TextView = itemView.findViewById(R.id.tvSha)
        val message: TextView = itemView.findViewById(R.id.tvMessage)
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val author: TextView = itemView.findViewById(R.id.tvAuthor)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        val githubCommitModel = getItem(position)
        githubCommitModel?.let {
            holder.sha.text = it.sha
            holder.message.text = it.commit.message
            holder.date.text = it.commit.author.getCommitDateAsString()
            holder.author.text = context.resources.getString(R.string.author, it.commit.author.name)
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