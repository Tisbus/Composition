package com.example.composition.presentation.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.R
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.usecases.GenerateQuestionUseCase
import com.example.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level
    ) : ViewModel() {

    private lateinit var gameSettings: GameSettings
    private var timer: CountDownTimer? = null
    private val repository = GameRepositoryImpl
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0


    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    init {
        startGame()
    }
    private fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestions()
        updateProgress()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            application.resources.getString (R.string.tv_question),
        countOfRightAnswers,
        gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions == 0) {
            return 0
        }
        return (countOfRightAnswers / countOfQuestions.toDouble() * 100).toInt()
    }

    private fun generateQuestions() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    fun chooseAnswers(number: Int) {
        checkRightAnswers(number)
        updateProgress()
        generateQuestions()
    }

    private fun checkRightAnswers(number: Int) {
        val rightAnswers = _question.value?.rightAnswers
        if (rightAnswers == number) {
            countOfRightAnswers++
            countOfQuestions++
        }
    }

    private fun getGameSettings() {
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = stringTimeBuilder(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun stringTimeBuilder(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    companion object {
        const val MILLIS_IN_SECONDS = 1000L
        const val SECONDS_IN_MINUTES = 60
    }
}