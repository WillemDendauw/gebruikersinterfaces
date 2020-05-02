package com.example.quiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.example.quiz.databinding.ActivityHintsBinding
import com.example.quiz.model.QuizMaster

class HintsActivity : AppCompatActivity() {

    val NO_HINT: Int = 123
    val TEXT_HINT: Int = 456
    val IMAGE_HINT: Int = 789

    val hintVisibility = ObservableField<Int>()

    companion object {
        val TEXT_HINT_VIEWED: String? = "be.ugent.oomt.quizapp.txtHintViewed"
        val IMAGE_HINT_VIEWED: String = "be.ugent.oomt.quizapp.imageHintViewed"

        fun createIntent(ctx: Context): Intent {
            return Intent(ctx, HintsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val binding: ActivityHintsBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_hints
        )

        binding.quizMaster = QuizMaster
        binding.handler = this

        //initiële waarden zetten
        hintVisibility.set(NO_HINT)
    }

    fun onShowTextHint() {
        hintVisibility.set(TEXT_HINT)
        val i: Intent = intent
        i.putExtra(TEXT_HINT_VIEWED, true)
        setResult(RESULT_OK, i)
    }

    fun onShowImageHint() {
        hintVisibility.set(IMAGE_HINT)
        val i: Intent = intent
        i.putExtra(IMAGE_HINT_VIEWED, true)
        setResult(RESULT_OK,i)
    }
}