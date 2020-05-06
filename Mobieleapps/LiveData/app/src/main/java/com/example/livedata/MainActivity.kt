package com.example.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.example.livedata.databinding.ActivityMainBinding
import com.example.livedata.model.QuoteDAO

class MainActivity : AppCompatActivity() {

    lateinit var dao: QuoteDAO
    var currentQuoteIndex: Int = 0

    val quote: ObservableField<String> = ObservableField()
    val author: ObservableField<String> = ObservableField()
    val progress: ObservableInt = ObservableInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        currentQuoteIndex = 0
        dao = QuoteDAO(this)
        quote.set(dao.get(currentQuoteIndex).text)
        author.set(dao.get(currentQuoteIndex).author)

        val timer = object : CountDownTimer(5000, 10) {
            override fun onTick(millisUntilFinished: Long) {
                progress.set(((100 * (5000 - millisUntilFinished) / 5000).toInt()))
            }

            override fun onFinish() {
                currentQuoteIndex = (currentQuoteIndex + 1) % dao.size
                quote.set(dao.get(currentQuoteIndex).text)
                author.set(dao.get(currentQuoteIndex).author)
                progress.set(0)
                this.start()
            }
        }.start()
    }
}
