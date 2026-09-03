package com.example.quizapp.data.model

enum class Difficulty(val displayName: String, val description: String) {
    EASY("Easy", "Pertanyaan umum & trivia dasar"),
    MEDIUM("Medium", "Pertanyaan sains, geografi & pengetahuan umum"),
    HARD("Hard", "Pertanyaan lanjutan, sains & ilmu komputer")
}
