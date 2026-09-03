package com.example.quizapp

import com.example.quizapp.data.generator.QuestionGenerator
import com.example.quizapp.data.model.Difficulty
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class QuestionGeneratorTest {

    private val generator = QuestionGenerator()

    @Test
    fun `generateQuestions returns requested count of questions`() {
        val easyQuestions = generator.generateQuestions(Difficulty.EASY, 10)
        assertEquals(10, easyQuestions.size)

        val mediumQuestions = generator.generateQuestions(Difficulty.MEDIUM, 10)
        assertEquals(10, mediumQuestions.size)

        val hardQuestions = generator.generateQuestions(Difficulty.HARD, 10)
        assertEquals(10, hardQuestions.size)
    }

    @Test
    fun `generated questions have 4 options and valid correct answer`() {
        val questions = generator.generateQuestions(Difficulty.EASY, 10)

        questions.forEach { question ->
            assertEquals(4, question.options.size)
            assertTrue(question.correctAnswerIndex in 0..3)
            assertNotNull(question.correctAnswer)
            assertTrue(question.correctAnswer.isNotBlank())
        }
    }

    @Test
    fun `generated question IDs are sequential 1 to 10`() {
        val questions = generator.generateQuestions(Difficulty.HARD, 10)

        questions.forEachIndexed { index, question ->
            assertEquals(index + 1, question.id)
        }
    }
}
