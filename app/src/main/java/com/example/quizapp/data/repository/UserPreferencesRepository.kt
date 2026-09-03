package com.example.quizapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.quizapp.data.model.Difficulty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.quizDataStore: DataStore<Preferences> by preferencesDataStore(name = "quiz_preferences")

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    constructor(context: Context) : this(context.quizDataStore)

    companion object {
        private val KEY_BEST_SCORE_EASY = intPreferencesKey("best_score_easy")
        private val KEY_BEST_SCORE_MEDIUM = intPreferencesKey("best_score_medium")
        private val KEY_BEST_SCORE_HARD = intPreferencesKey("best_score_hard")

        private fun getKeyForDifficulty(difficulty: Difficulty): Preferences.Key<Int> {
            return when (difficulty) {
                Difficulty.EASY -> KEY_BEST_SCORE_EASY
                Difficulty.MEDIUM -> KEY_BEST_SCORE_MEDIUM
                Difficulty.HARD -> KEY_BEST_SCORE_HARD
            }
        }
    }

    fun getBestScore(difficulty: Difficulty): Flow<Int> {
        val key = getKeyForDifficulty(difficulty)
        return dataStore.data.map { preferences ->
            preferences[key] ?: 0
        }
    }

    suspend fun saveScoreIfBest(difficulty: Difficulty, newScore: Int): Boolean {
        val key = getKeyForDifficulty(difficulty)
        val currentBest = getBestScore(difficulty).first()
        return if (newScore > currentBest) {
            dataStore.edit { preferences ->
                preferences[key] = newScore
            }
            true
        } else {
            false
        }
    }
}
