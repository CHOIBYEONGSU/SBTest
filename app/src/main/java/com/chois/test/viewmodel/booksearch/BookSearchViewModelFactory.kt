package com.chois.test.viewmodel.booksearch

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BookSearchViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BookSearchViewModel::class.java)) {
            BookSearchViewModel(application) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}