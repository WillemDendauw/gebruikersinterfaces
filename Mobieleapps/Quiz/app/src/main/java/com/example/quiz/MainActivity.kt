package com.example.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.quiz.databinding.ActivityMainBinding
import com.example.quiz.model.QuizMaster

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.quizMaster = QuizMaster
        binding.handler = this
    }

    fun onHintClick(){
        val toast = Toast.makeText(applicationContext, "test", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onOkClick(){

    }
}
