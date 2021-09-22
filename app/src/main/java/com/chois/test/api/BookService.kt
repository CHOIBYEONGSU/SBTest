package com.chois.spoontest.api

import com.chois.test.BuildConfig


object BookService {

    private const val SEARCH_API_URL = BuildConfig.SEARCH_API_URL

    val client = BookApiClient().getClient(SEARCH_API_URL)?.create(BookApi::class.java)
}