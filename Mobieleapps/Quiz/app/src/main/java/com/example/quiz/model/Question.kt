package com.example.quiz.model

class Question(
    val question: String,
    val answer: String,
    val textHint: String,
    val imageUriHint: String
) {
    companion object QuestionIdCounter {
        private var count: Int = 0

        fun getCount(): Int {
            return count++
        }
    }

    val questionID = QuestionIdCounter.getCount()
}