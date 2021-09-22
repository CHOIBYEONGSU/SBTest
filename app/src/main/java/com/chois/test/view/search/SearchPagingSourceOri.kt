package com.chois.test.view.search

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chois.spoontest.api.SearchService
import com.chois.test.model.data.Search

class SearchPagingSourceOri(
    private val searchService: SearchService,
    private val query: String
) : PagingSource<Int, Search>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Search> {
        return try {
            val nextPageNumber = params.key ?: 1
            val body = searchService.client?.getSearchList(query, nextPageNumber)?.body()
            Log.v("데이터", "${body!!.books.size} ㅇㅇㅇ")
            LoadResult.Page(
                data = body!!.books,
                prevKey = if (body.page.toInt() == 1) null else body.page.toInt() - 1,
                nextKey = if (body.books.isEmpty()) null else body.page.toInt() + 1
            )
        } catch (e: Exception) {
            Log.v("데이터", "$e ㅇㅇㅇ")
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