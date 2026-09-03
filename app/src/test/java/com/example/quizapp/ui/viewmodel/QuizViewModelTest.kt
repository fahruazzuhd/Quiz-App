package com.example.quizapp.ui.viewmodel

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.quizapp.MainDispatcherRule
import com.example.quizapp.data.model.Difficulty
import com.example.quizapp.data.repository.UserPreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var repository: UserPreferencesRepository
    private lateinit var viewModel: QuizViewModel

    @Before
    fun setup() {
        val testDataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { tmpFolder.newFile("test_quiz_vm_prefs.preferences_pb") }
        )
        repository = UserPreferencesRepository(testDataStore)
        viewModel = QuizViewModel(repository)
    }

    @Test
    fun `startQuiz initializes 10 questions and resets state`() {
        viewModel.startQuiz(Difficulty.MEDIUM)

        val state = viewModel.uiState.value
        assertEquals(Difficulty.MEDIUM, state.selectedDifficulty)
        assertEquals(10, state.questions.size)
        assertEquals(0, state.currentQuestionIndex)
        assertEquals(0, state.score)
        assertFalse(state.isQuizFinished)
        assertNotNull(state.currentQuestion)
    }

    @Test
    fun `submitAnswer adds 10 score when answer is correct`() {
        viewModel.startQuiz(Difficulty.EASY)
        val currentQuestion = viewModel.uiState.value.currentQuestion!!
        val correctAnswerIndex = currentQuestion.correctAnswerIndex

        viewModel.submitAnswer(correctAnswerIndex)

        val state = viewModel.uiState.value
        assertTrue(state.isAnswerSubmitted)
        assertEquals(correctAnswerIndex, state.selectedOptionIndex)
        assertEquals(10, state.score)
    }

    @Test
    fun `submitAnswer adds 0 score when answer is wrong`() {
        viewModel.startQuiz(Difficulty.EASY)
        val currentQuestion = viewModel.uiState.value.currentQuestion!!
        val wrongAnswerIndex = (currentQuestion.correctAnswerIndex + 1) % 4

        viewModel.submitAnswer(wrongAnswerIndex)

        val state = viewModel.uiState.value
        assertTrue(state.isAnswerSubmitted)
        assertEquals(0, state.score)
    }

    @Test
    fun `nextQuestion advances index and finishes quiz at question 10`() = runTest {
        viewModel.startQuiz(Difficulty.EASY)

        for (i in 0 until 10) {
            val correctIdx = viewModel.uiState.value.currentQuestion!!.correctAnswerIndex
            viewModel.submitAnswer(correctIdx)
            viewModel.nextQuestion()
        }

        val finalState = viewModel.uiState.value
        assertTrue(finalState.isQuizFinished)
        assertEquals(100, finalState.score)
        assertTrue(finalState.isNewHighScore)
    }
}
