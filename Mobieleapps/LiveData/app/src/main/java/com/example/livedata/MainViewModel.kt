package com.example.livedata

import android.content.Context
import android.os.CountDownTimer
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.livedata.model.QuoteDAO

class MainViewModel(c: Context) : ViewModel() {

    var dao: QuoteDAO = QuoteDAO(c)
    private var currentQuoteIndex: Int = 0

    val quote: MutableLiveData<String> = MutableLiveData(dao.get(currentQuoteIndex).text)
    val author: MutableLiveData<String> = MutableLiveData(dao.get(currentQuoteIndex).author)
    val progress: MutableLiveData<Int> = MutableLiveData()

    val timer = object : CountDownTimer(5000, 10) {
        override fun onTick(millisUntilFinished: Long) {
            progress.postValue(((100 * (5000 - millisUntilFinished) / 5000).toInt()))
        }

        override fun onFinish() {
            currentQuoteIndex = (currentQuoteIndex + 1) % dao.size
            quote.postValue(dao.get(currentQuoteIndex).text)
            author.postValue(dao.get(currentQuoteIndex).author)
            progress.postValue(0)
            this.start()
        }
    }.start()
}