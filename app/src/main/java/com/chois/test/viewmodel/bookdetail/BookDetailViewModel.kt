package com.chois.test.viewmodel.bookdetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chois.test.R
import com.chois.test.global.Event
import com.chois.test.model.data.Book
import com.chois.test.model.repository.bookdetail.BookDeatailRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class BookDetailViewModel(var application: Application, var isbn13: String?) : ViewModel() {

    private var job: Job? = null

    private val bookDetailRepository = BookDeatailRepository()

    private val progressMutableLiveData = MutableLiveData<Boolean>()
    private val errorMutableLiveData = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val bookDetailMutableLiveData: MutableLiveData<Book> by lazy {
        MutableLiveData<Book>().also {
            getBookDetailData()
        }
    }
    private val toastMsgEventLiveData: MutableLiveData<Event<String>> =
        MutableLiveData<Event<String>>()
    private val finishEventLiveData: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>()

    fun getBookDetailData() {
        if (isbn13.isNullOrBlank()){
            showToastMsg(application.getString(R.string.error_msg))
            doFinish(true)
        } else {
            setProgressMutableLiveData(true)
            job?.cancel()
            job = viewModelScope.launch {
                val response = bookDetailRepository.getServerBookDetailData(isbn13!!)
                setProgressMutableLiveData(false)
                if (response?.isSuccessful == true) {
                    val body = response.body()
                    bookDetailMutableLiveData.postValue(body)
                    setErrorMutableLiveData(false)
                } else {
                    setErrorMutableLiveData(true)
                }
            }
        }
    }

    fun getBookDetailLiveData(): LiveData<Book> {
        return bookDetailMutableLiveData
    }

    fun getProgressLiveData(): LiveData<Boolean> {
        return progressMutableLiveData
    }

    private fun setProgressMutableLiveData(state: Boolean) {
        progressMutableLiveData.postValue(state)
    }

    fun getErrorLiveData(): LiveData<Boolean> {
        return errorMutableLiveData
    }

    private fun setErrorMutableLiveData(state: Boolean) {
        errorMutableLiveData.postValue(state)
    }

    private fun showToastMsg(msg: String) {
        toastMsgEventLiveData.postValue(Event(msg))
    }

    fun getToastMsgEventLiveData(): LiveData<Event<String>> {
        return toastMsgEventLiveData
    }
    private fun doFinish(isFinish: Boolean) {
        finishEventLiveData.postValue(Event(isFinish))
    }

    fun getFinishEventLiveData(): LiveData<Event<Boolean>> {
        return finishEventLiveData
    }
}