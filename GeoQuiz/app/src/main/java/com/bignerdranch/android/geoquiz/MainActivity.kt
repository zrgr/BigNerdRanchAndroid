package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.questionTextView.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.trueButton.setOnClickListener {
            toggleNavButtons(false)
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            toggleNavButtons(false)
            checkAnswer(false)
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        updateQuestion()
    }

    private fun showToast(message: Int) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showSnackbar(message: Int) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
        toggleNavButtons(true)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId: Int

        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct_toast
            quizViewModel.currentScore += 1
        } else {
            messageResId = R.string.incorrect_toast
        }

        showToast(messageResId)

        if(quizViewModel.shouldFinalScoreBeDisplayed())
            displayScore()
    }

    private fun toggleNavButtons(enable: Boolean) {
        binding.trueButton.isEnabled = enable
        binding.falseButton.isEnabled = enable
    }

    private fun displayScore() {
        var scoreAsPercentage = quizViewModel.getFinalScore()
        Toast.makeText(
            this,
            "You scored ${scoreAsPercentage}%",
            Toast.LENGTH_SHORT
        ).show()
    }

    //region lifecycle

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    //endregion
}