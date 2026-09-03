package com.example.quizapp.ui.viewmodel

import com.example.quizapp.data.model.Difficulty
import com.example.quizapp.data.model.Question

data class QuizUiState(
    val selectedDifficulty: Difficulty = Difficulty.EASY,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswerSubmitted: Boolean = false,
    val score: Int = 0,
    val isQuizFinished: Boolean = false,
    val isNewHighScore: Boolean = false,
    val bestScore: Int = 0
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)

    val totalQuestions: Int
        get() = questions.size

    val progressFraction: Float
        get() = if (totalQuestions > 0) (currentQuestionIndex + 1).toFloat() / totalQuestions else 0f

    val progressText: String
        get() = if (totalQuestions > 0) "Soal ${currentQuestionIndex + 1} dari $totalQuestions" else ""
}
