package com.chois.test.model.repository.bookdetail

import com.chois.spoontest.api.BookService
import com.chois.test.model.data.Book
import retrofit2.Response

class BookDeatailRepository {
    private val githubClient = BookService.client

    suspend fun getServerBookDetailData(isbn13: String): Response<Book>? {
        return try {
            githubClient?.getBookDetail(isbn13)
        } catch (e: Exception){
            null
        }
    }

}