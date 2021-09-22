package com.chois.test.view.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chois.spoontest.api.SearchService
import com.chois.test.model.data.Search

class SearchPagingSource(
    private val searchService: SearchService,
    private val query: MutableList<String>
) : PagingSource<Int, Search>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Search> {
        return try {
            val page = params.key ?: 1
            val booksList = mutableListOf<Search>()

            query.forEachIndexed{ index, q ->
                if (q.isNotBlank()){
                    val body = searchService.client?.getSearchList(q, page)?.body()
                    if (body != null) {
                        if (body.books.isEmpty()){
                            query[index] = ""
                        } else {
                            booksList.addAll(body.books)
                        }
                    }
                }
            }

            val nextKey = if (booksList.isEmpty()) {
                null
            } else {
                page + 1
            }

            LoadResult.Page(
                data = booksList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}