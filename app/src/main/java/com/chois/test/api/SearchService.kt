package com.chois.spoontest.api

import com.chois.test.BuildConfig


object SearchService {

    private const val SEARCH_API_URL = BuildConfig.SEARCH_API_URL

    val client = ApiClient().getClient(SEARCH_API_URL)?.create(SearchApi::class.java)
}