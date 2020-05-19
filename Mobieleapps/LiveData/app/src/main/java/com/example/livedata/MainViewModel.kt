package com.example.livedata

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.livedata.model.QuoteDAO

class MainViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var dao: QuoteDAO;
    var currentQuoteIndex: Int = 0

    val quote: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val author: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val progress: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        currentQuoteIndex = 0;
        dao = QuoteDAO(application.applicationContext)
        quote.value = "\"You miss 100% of the shots you don't take.\" - Wayne Gretzky\""
        author.value = "Michael Scott"
        //quote.value = dao.get(currentQuoteIndex).text
        //author.value = dao.get(currentQuoteIndex).author

        val timer = object : CountDownTimer(5000, 10 ){
            override fun onTick(millesUntilFinished: Long){
                progress.value = (((100 * (5000 - millesUntilFinished) / 5000).toInt()))
            }

            override fun onFinish() {
                currentQuoteIndex = (currentQuoteIndex + 1) % dao.size
                quote.value = dao.get(currentQuoteIndex).text
                author.value = dao.get(currentQuoteIndex).author
                progress.value = 0
                this.start()
            }
        }.start()
    }

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