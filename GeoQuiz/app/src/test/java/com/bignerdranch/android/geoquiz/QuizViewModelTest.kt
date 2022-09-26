package com.bignerdranch.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class QuizViewModelTest {

    @Test
    fun providesExpectedQuestionText() {
        val quizViewModel = QuizViewModel()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun providesExpectedQuestionAnswer() {
        val quizViewModel = QuizViewModel()
        assertEquals(true, quizViewModel.currentQuestionAnswer)
    }

    @Test
    fun wrapsAroundQuestionBank() {
        val quizViewModel = QuizViewModel()
        quizViewModel.currentIndex = 5
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun movesToPreviousQuestion() {
        val quizViewModel = QuizViewModel()
        quizViewModel.currentIndex = 3
        assertEquals(R.string.question_africa, quizViewModel.currentQuestionText)
        quizViewModel.moveToPrevious()
        assertEquals(R.string.question_mideast, quizViewModel.currentQuestionText)
    }
}