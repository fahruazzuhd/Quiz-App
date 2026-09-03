package com.example.quizapp.data.model

data class Question(
    val id: Int,
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
) {
    val correctAnswer: String
        get() = options.getOrElse(correctAnswerIndex) { "" }
}
