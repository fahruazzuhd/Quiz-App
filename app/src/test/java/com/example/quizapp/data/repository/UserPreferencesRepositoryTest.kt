package com.example.quizapp.data.repository

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.quizapp.data.model.Difficulty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
class UserPreferencesRepositoryTest {

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var repository: UserPreferencesRepository

    @Before
    fun setup() {
        val testDataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { tmpFolder.newFile("test_quiz_prefs.preferences_pb") }
        )
        repository = UserPreferencesRepository(testDataStore)
    }

    @Test
    fun `default best score is zero`() = runTest {
        val easyBest = repository.getBestScore(Difficulty.EASY).first()
        val hardBest = repository.getBestScore(Difficulty.HARD).first()

        assertEquals(0, easyBest)
        assertEquals(0, hardBest)
    }

    @Test
    fun `saveScoreIfBest updates score when new score is higher`() = runTest {
        val isUpdated1 = repository.saveScoreIfBest(Difficulty.EASY, 70)
        assertTrue(isUpdated1)
        assertEquals(70, repository.getBestScore(Difficulty.EASY).first())

        val isUpdated2 = repository.saveScoreIfBest(Difficulty.EASY, 90)
        assertTrue(isUpdated2)
        assertEquals(90, repository.getBestScore(Difficulty.EASY).first())
    }

    @Test
    fun `saveScoreIfBest does not update score when new score is lower or equal`() = runTest {
        repository.saveScoreIfBest(Difficulty.MEDIUM, 80)

        val isUpdated = repository.saveScoreIfBest(Difficulty.MEDIUM, 50)
        assertFalse(isUpdated)
        assertEquals(80, repository.getBestScore(Difficulty.MEDIUM).first())
    }
}
