package com.chois.test.model.repository.bookdetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chois.spoontest.api.SearchService
import com.chois.test.model.data.Book
import com.chois.test.model.data.Search
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import kotlin.collections.ArrayList

class BookDeatailRepository {
    private val githubClient = SearchService.client

    suspend fun getServerBookDetailData(isbn13: String): Response<Book>? {
        return try {
            githubClient?.getBookDetail(isbn13)
        } catch (e: Exception){
            null
        }
    }

}