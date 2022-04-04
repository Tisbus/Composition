package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult
import java.util.*

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult
    private var _bind: FragmentGameFinishedBinding? = null
    private val bind: FragmentGameFinishedBinding
        get() = _bind ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        bind.returnButton.setOnClickListener {
            retryGame()
        }
    }

    private fun bindViews() {
        with(bind){
            imageResult.setImageResource(getImgResource())
            tvNeedRightAnswers.text = String.format(
                getString(R.string.count_right_answers),
                gameResult.gameSettings.minCountOfRightAnswers
            )
            tvYouBill.text =
                String.format(getString(R.string.your_bill), gameResult.countOfRightAnswers)
            tvRequiredPercentAnswers.text = String.format(
                getString(R.string.percent_right_answers),
                gameResult.gameSettings.minPercentRightAnswers
            )
            tvPercentRightAnswers.text =
                String.format(getString(R.string.percent_answers), getPercentOfRightAnswers())
        }
    }

    private fun getPercentOfRightAnswers() = with(gameResult){
        if(countOfQuestion == 0){
            0
        }else{
            ((countOfRightAnswers / countOfQuestion.toDouble())*100).toInt()
        }
    }

    private fun getImgResource() : Int {
        val result = if (gameResult.winner) {
            R.drawable.smile
        } else {
            R.drawable.nosmile
        }
        return result
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_SETTINGS)?.let{
            gameResult = it
        }
    }

    companion object {

        private const val KEY_SETTINGS = "settings"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_SETTINGS, gameResult)
                }
            }
        }
    }
}