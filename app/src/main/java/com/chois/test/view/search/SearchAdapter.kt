package com.chois.test.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chois.test.databinding.RecyclerViewItemSearchBinding
import com.chois.test.model.data.Search

class SearchAdapter (private val listener : OnItemClickListener) : PagingDataAdapter<Search, SearchAdapter.SearchViewHolder>(diffCallback) {
    interface OnItemClickListener {
        fun onItemClick(search: Search?)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Search>() {
            override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem.isbn13 == newItem.isbn13
            }
            override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = RecyclerViewItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            listener.onItemClick(getItem(position))
        }
    }

    class SearchViewHolder(private val binding: RecyclerViewItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(search: Search) {
            binding.search = search
        }
    }
}