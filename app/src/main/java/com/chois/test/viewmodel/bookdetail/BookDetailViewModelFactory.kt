package com.chois.test.viewmodel.bookdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BookDetailViewModelFactory(private val application: Application, private val isbn13: String?) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BookDetailViewModel::class.java)) {
            BookDetailViewModel(application, isbn13) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}