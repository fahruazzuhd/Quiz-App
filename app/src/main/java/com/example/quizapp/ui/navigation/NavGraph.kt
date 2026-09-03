package com.example.quizapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.ui.screen.HomeScreen
import com.example.quizapp.ui.screen.QuizScreen
import com.example.quizapp.ui.screen.ResultScreen
import com.example.quizapp.ui.viewmodel.QuizViewModel

object Routes {
    const val HOME = "home"
    const val QUIZ = "quiz"
    const val RESULT = "result"
}

@Composable
fun QuizNavGraph(
    viewModel: QuizViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onStartQuiz = {
                    navController.navigate(Routes.QUIZ)
                }
            )
        }

        composable(Routes.QUIZ) {
            QuizScreen(
                viewModel = viewModel,
                onQuizFinished = {
                    navController.navigate(Routes.RESULT) {
                        popUpTo(Routes.HOME)
                    }
                }
            )
        }

        composable(Routes.RESULT) {
            ResultScreen(
                viewModel = viewModel,
                onPlayAgain = {
                    navController.navigate(Routes.QUIZ) {
                        popUpTo(Routes.HOME)
                    }
                },
                onHomeClick = {
                    navController.popBackStack(Routes.HOME, inclusive = false)
                }
            )
        }
    }
}
