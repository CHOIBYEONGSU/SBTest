package com.chois.test.view.bookdetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.chois.test.R
import com.chois.test.databinding.ActivityBookDeatilBinding
import com.chois.test.global.EventObserver
import com.chois.test.global.showToast
import com.chois.test.viewmodel.bookdetail.BookDetailViewModel
import com.chois.test.viewmodel.bookdetail.BookDetailViewModelFactory

class BookDeatilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDeatilBinding
    private lateinit var viewModel: BookDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_deatil)
        val isbn13 = intent.getStringExtra("isbn13")
        viewModel = ViewModelProvider(this, BookDetailViewModelFactory(application, isbn13))
            .get(BookDetailViewModel::class.java)
        binding.viewmodel = viewModel

        initObserve()
    }

    private fun initObserve() {
        viewModel.getBookDetailLiveData().observe(this) { bookData ->
            binding.book = bookData
        }
        viewModel.getProgressLiveData().observe(this) { newState ->
            binding.bookDetailLoadProgressbar.isVisible = newState
        }
        viewModel.getErrorLiveData().observe(this) { newError ->
            binding.bookDetailErrorConstraintlayout.isVisible = newError
        }
        viewModel.getToastMsgEventLiveData().observe(this, EventObserver {
            showToast(it)
        })
        viewModel.getFinishEventLiveData().observe(this, EventObserver {
            if (it){
                finish()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}