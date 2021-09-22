package com.chois.test.view.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.chois.test.R
import com.chois.test.databinding.ActivitySearchBinding
import com.chois.test.global.EventObserver
import com.chois.test.global.hideKeyboard
import com.chois.test.global.showToast
import com.chois.test.model.data.Search
import com.chois.test.view.bookdetail.BookDeatilActivity
import com.chois.test.viewmodel.search.SearchViewModel
import com.chois.test.viewmodel.search.SearchViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var pagingAdapter: SearchAdapter
    private var searchJob: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.activity = this
        viewModel = ViewModelProvider(this, SearchViewModelFactory(application))
            .get(SearchViewModel::class.java)

        initRecyclerView()
        initObserve()
    }

    private fun initRecyclerView(){

        pagingAdapter = SearchAdapter(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(search: Search?) {
                if (search != null) {
                    val intent = Intent(this@SearchActivity, BookDeatilActivity::class.java)
                    intent.putExtra("isbn13", search.isbn13)
                    startActivity(intent)
                }
            }
        })

        binding.searchSearchRecyclerview.adapter = pagingAdapter
        binding.searchSearchRecyclerview.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        binding.searchSearchEdittext.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    getSearchData()
                    true
                }
                else -> false
            }
        }

        pagingAdapter.addLoadStateListener { loadState ->
            binding.apply {
                searchLoadingProgressbar.isVisible = loadState.source.refresh is LoadState.Loading
                if (loadState.source.refresh is LoadState.Error) {
                    searchErrorConstraintlayout.isVisible = true
                    searchSearchRecyclerview.isVisible = false
                } else if (loadState.source.append is LoadState.Error) {
                    searchErrorConstraintlayout.isVisible = true
                    searchSearchRecyclerview.isVisible = true
                } else {
                    searchErrorConstraintlayout.isVisible = false
                    if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && pagingAdapter.itemCount < 1) {
                        searchSearchRecyclerview.isVisible = false
                        searchEmptyTextview.isVisible = true
                    } else {
                        searchSearchRecyclerview.isVisible = true
                        searchEmptyTextview.isVisible = false
                    }
                }
            }
        }


        lifecycleScope.launch {
            viewModel.initSearchBookData()?.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun initObserve(){
        viewModel.getToastMsgEventLiveData().observe(this, EventObserver {
            showToast(it)
        })
    }

    fun getSearchData() {
        hideKeyboard(currentFocus ?: View(this))
        binding.searchSearchEdittext.clearFocus()

        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchBookData(binding.searchSearchEdittext.text.toString())
                .collectLatest { pagingData ->
                    pagingAdapter.submitData(pagingData)
                }
        }
    }

    fun adapterRetry() {
        pagingAdapter.retry()
    }
}