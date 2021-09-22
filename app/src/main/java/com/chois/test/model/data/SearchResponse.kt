package com.chois.test.model.data

data class SearchResponse(
    val error : String,
    val total : String,
    val page : String,
//    val books: Array<Search> = emptyArray(),
    val books: List<Search>,

    )