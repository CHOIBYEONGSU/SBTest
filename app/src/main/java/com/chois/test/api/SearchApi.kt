package com.chois.spoontest.api

import com.chois.test.model.data.Book
import com.chois.test.model.data.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchApi {
    @GET("/1.0/search/{query}/{page}")
    suspend fun getSearchList(
        @Path("query") query: String,
        @Path("page") page: Int,
    ): Response<SearchResponse>

    @GET("/1.0/books/{isbn13}")
    suspend fun getBookDetail(
        @Path("isbn13") isbn13: String,
    ): Response<Book>

}