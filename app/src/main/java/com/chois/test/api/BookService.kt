package com.chois.test.api

object BookService {
    private const val SEARCH_API_URL = "https://api.itbook.store"

    val client = BookApiClient().getClient(SEARCH_API_URL)?.create(BookApi::class.java)
}