package com.chois.test.viewmodel.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chois.test.R
import com.chois.test.global.Event
import com.chois.test.model.data.Search
import com.chois.test.model.repository.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class SearchViewModel(var application: Application) : ViewModel() {

    private val searchRepository = SearchRepository()

    var queryString: String = ""
    private var searchResult: Flow<PagingData<Search>>? = null

    private val toastMsgEvenLiveData: MutableLiveData<Event<String>> =
        MutableLiveData<Event<String>>()

    fun initSearchBookData(): Flow<PagingData<Search>>? {
        return searchResult
    }

    fun searchBookData(query: String): Flow<PagingData<Search>> {

        if (query.isBlank()) {
            showToastMsg(application.getString(R.string.search_edittext_hint))
            return emptyFlow()
        }

        if (query.contains("-") && query.contains("|")) {
            showToastMsg(application.getString(R.string.search_query_error_msg))
            return emptyFlow()
        } else if (query.contains("-")) {
            val splitQuery = query.split("-")
            if (splitQuery[0].isBlank() || splitQuery[1].isBlank() || splitQuery[0] == splitQuery[1]) {
                showToastMsg(application.getString(R.string.search_query_error_msg))
                return emptyFlow()
            }
        } else if (query.contains("|")) {
            val splitQuery = query.split("|")
            if (splitQuery[0].isBlank() || splitQuery[1].isBlank() || splitQuery[0] == splitQuery[1]) {
                showToastMsg(application.getString(R.string.search_query_error_msg))
                return emptyFlow()
            }
        }

        this.queryString = query
        val newResult = searchRepository.getSearchListDataByPaging(query)
            .cachedIn(viewModelScope)
        searchResult = newResult
        return newResult
    }


    private fun showToastMsg(msg: String) {
        toastMsgEvenLiveData.postValue(Event(msg))
    }

    fun getToastMsgEventLiveData(): LiveData<Event<String>> {
        return toastMsgEvenLiveData
    }
}