package com.example.quiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.quiz.databinding.ActivityMainBinding
import com.example.quiz.model.QuizMaster

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private companion object {
        val HINT_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG,"Create")
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        binding.quizMaster = QuizMaster
        binding.handler = this
    }

    override fun onPause() {
        Log.i(TAG, "Pause")
        super.onPause()
    }

    override fun onDestroy() {
        Log.i(TAG, "Destroy")
        super.onDestroy()
    }

    override fun onStart() {
        Log.i(TAG, "Start")
        super.onStart()
    }

    override fun onResume() {
        Log.i(TAG, "Resume")
        super.onResume()
    }

    override fun onStop() {
        Log.i(TAG, "Stop")
        super.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == HINT_REQUEST){
            var feedback : String = ""
            if (resultCode == Activity.RESULT_OK){
                if(data != null){
                    if(data.getBooleanExtra(HintsActivity.TEXT_HINT_VIEWED, false)){
                        feedback += "text hint shown"
                    }
                    if(data.getBooleanExtra(HintsActivity.IMAGE_HINT_VIEWED,false)){
                        feedback += "\n" + "image hint shown"
                    }
                }
            } else {
                feedback = "no hints shown"
            }
            val toast = Toast.makeText(applicationContext, feedback, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun onHintClick(view: View){
        //show toast
        //val toast = Toast.makeText(applicationContext, "test", Toast.LENGTH_SHORT)
        //toast.show()

        val i = HintsActivity.createIntent(this)
        startActivityForResult(i, HINT_REQUEST)
    }

    @SuppressLint("ShowToast")
    fun onOkClick(){
        val duration = Toast.LENGTH_SHORT
        val toast: Toast
        if(QuizMaster.validateAnswer()){
            QuizMaster.nextQuestion()
            toast = Toast.makeText(
                applicationContext,
                QuizMaster.userAnswer.get() + " is correct!",
                duration
            )
            Log.i(TAG, QuizMaster.currentQuestion.get()?.question ?: "")
        } else {
            toast = Toast.makeText(
                applicationContext,
                QuizMaster.userAnswer.get() + " is incorrect!",
                duration
            )
        }
        QuizMaster.userAnswer.set("")
        toast.show()
    }
}
