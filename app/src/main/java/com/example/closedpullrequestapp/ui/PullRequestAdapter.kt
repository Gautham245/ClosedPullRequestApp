package com.example.closedpullrequestapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.closedpullrequestapp.data.remote.dto.GitPullResponseItem
import com.example.closedpullrequestapp.databinding.GitRequestItemBinding

class PullRequestAdapter :
    ListAdapter<GitPullResponseItem, PullRequestAdapter.ProductViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<GitPullResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GitPullResponseItem,
            newItem: GitPullResponseItem,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GitPullResponseItem,
            newItem: GitPullResponseItem,
        ): Boolean {
            return oldItem == newItem
        }
    }


    inner class ProductViewHolder(val gitRequestItemBinding: GitRequestItemBinding) :
        RecyclerView.ViewHolder(gitRequestItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(GitRequestItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val gitRequest = getItem(position)
        holder.gitRequestItemBinding.apply {
            gitPullData = gitRequest
            userImage.loadImage(gitRequest.user.avatar_url)
        }
    }

    fun ImageView.loadImage(uri: String) {
        Glide.with(this.context)
            .load(uri)
            .circleCrop()
            .into(this)
    }
}