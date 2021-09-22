package com.chois.test.model.repository.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.chois.spoontest.api.SearchService
import com.chois.test.global.Constants.Companion.SEARCH_PAGING_SIZE
import com.chois.test.model.data.Search
import com.chois.test.view.search.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class SearchRepository {
    private val searchService = SearchService

    fun getSearchListDataByPaging(query: String): Flow<PagingData<Search>> {
        if (query.contains("-")) {
            val searchWord = query.split("-")[0]
            val exceptWord = query.split("-")[1]

            if (searchWord.isNotEmpty() && exceptWord.isNotEmpty()) {
                return Pager(
                    config = PagingConfig(pageSize = SEARCH_PAGING_SIZE),
                    pagingSourceFactory = { SearchPagingSource(searchService,
                        searchWord.split("|").toMutableList()
                    ) }
                ).flow
                    .map { pagingData ->
                        pagingData.filter { search ->
                            !search.title.contains(
                                exceptWord,
                                ignoreCase = true
                            )
                        }
                    }
            } else {
                return emptyFlow()
            }
        } else {
            return Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { SearchPagingSource(searchService,
                    query.split("|").toMutableList()
                ) }
            ).flow
        }
    }

}