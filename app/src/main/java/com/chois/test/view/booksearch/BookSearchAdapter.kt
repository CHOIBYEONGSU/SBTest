package com.chois.test.view.booksearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chois.test.databinding.RecyclerViewItemBookSearchBinding
import com.chois.test.model.data.Search

class BookSearchAdapter (private val listener : OnItemClickListener) : PagingDataAdapter<Search, BookSearchAdapter.SearchViewHolder>(diffCallback) {
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
        val binding = RecyclerViewItemBookSearchBinding.inflate(
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

    class SearchViewHolder(private val binding: RecyclerViewItemBookSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(search: Search) {
            binding.search = search
        }
    }
}