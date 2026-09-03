package com.example.quizapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.generator.QuestionGenerator
import com.example.quizapp.data.model.Difficulty
import com.example.quizapp.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: UserPreferencesRepository,
    private val questionGenerator: QuestionGenerator = QuestionGenerator()
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        selectDifficulty(Difficulty.EASY)
    }

    fun selectDifficulty(difficulty: Difficulty) {
        _uiState.update { it.copy(selectedDifficulty = difficulty) }
        viewModelScope.launch {
            repository.getBestScore(difficulty).collect { bestScore ->
                _uiState.update { currentState ->
                    currentState.copy(bestScore = bestScore)
                }
            }
        }
    }

    fun startQuiz(difficulty: Difficulty = _uiState.value.selectedDifficulty) {
        val questions = questionGenerator.generateQuestions(difficulty)
        _uiState.update {
            it.copy(
                selectedDifficulty = difficulty,
                questions = questions,
                currentQuestionIndex = 0,
                selectedOptionIndex = null,
                isAnswerSubmitted = false,
                score = 0,
                isQuizFinished = false,
                isNewHighScore = false
            )
        }
    }

    fun submitAnswer(optionIndex: Int) {
        val currentState = _uiState.value
        if (currentState.isAnswerSubmitted) return

        val currentQuestion = currentState.currentQuestion ?: return
        val isCorrect = optionIndex == currentQuestion.correctAnswerIndex
        val addedScore = if (isCorrect) 10 else 0

        _uiState.update {
            it.copy(
                selectedOptionIndex = optionIndex,
                isAnswerSubmitted = true,
                score = it.score + addedScore
            )
        }
    }

    fun nextQuestion() {
        val currentState = _uiState.value
        if (currentState.currentQuestionIndex + 1 < currentState.totalQuestions) {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = it.currentQuestionIndex + 1,
                    selectedOptionIndex = null,
                    isAnswerSubmitted = false
                )
            }
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        val currentState = _uiState.value
        val finalScore = currentState.score
        val difficulty = currentState.selectedDifficulty

        _uiState.update { it.copy(isQuizFinished = true) }

        viewModelScope.launch {
            val isHighScore = repository.saveScoreIfBest(difficulty, finalScore)
            if (isHighScore) {
                _uiState.update {
                    it.copy(
                        isNewHighScore = true,
                        bestScore = finalScore
                    )
                }
            }
        }
    }

    fun restartQuiz() {
        startQuiz(_uiState.value.selectedDifficulty)
    }

    class Factory(private val repository: UserPreferencesRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
                return QuizViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
