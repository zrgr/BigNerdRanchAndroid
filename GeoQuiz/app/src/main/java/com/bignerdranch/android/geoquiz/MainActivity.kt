package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
        updateCheatTokens()
    }

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

        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            blurCheatButton()

        updateCheatTokensText()
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

        when {
            quizViewModel.isCheater ->
                messageResId = R.string.judgment_toast
            userAnswer == correctAnswer -> {
                messageResId = R.string.correct_toast
                quizViewModel.currentScore += 1
            }
            else ->
                messageResId = R.string.incorrect_toast
        }

        resetCheatingStatus()

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

    private fun resetCheatingStatus() {
        quizViewModel.isCheater = false
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun blurCheatButton() {
        val effect = RenderEffect.createBlurEffect(
            10.0f,
            10.0f,
            Shader.TileMode.CLAMP
        )
        binding.cheatButton.setRenderEffect(effect)
    }

    private fun updateCheatTokens() {

        if (quizViewModel.isCheater) {

            quizViewModel.cheatTokens--

            if (quizViewModel.cheatTokens == 0) {
                binding.cheatButton.isEnabled = false
            }
        }

        updateCheatTokensText()
    }

    private fun updateCheatTokensText() {
        binding.cheatTokensTextView.text = getString(R.string.cheat_tokens, quizViewModel.cheatTokens)
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