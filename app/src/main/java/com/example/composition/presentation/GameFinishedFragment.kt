package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {

    private var _bind: FragmentGameFinishedBinding? = null
    private val bind: FragmentGameFinishedBinding
        get() = _bind ?: throw RuntimeException("FragmentGameFinishedBinding == null")
    private val args by navArgs<GameFinishedFragmentArgs>()
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        bind.returnButton.setOnClickListener {
            retryGame()
        }
    }

    private fun bindViews() {
        bind.gameResult = args.gameResult
        with(bind){
                imageResult.setImageResource(getImgResource())
/*                tvNeedRightAnswers.text = String.format(
                    getString(R.string.count_right_answers),
                    gameResult.gameSettings.minCountOfRightAnswers
                )
                tvYouBill.text =
                    String.format(getString(R.string.your_bill), gameResult.countOfRightAnswers)
                tvRequiredPercentAnswers.text = String.format(
                    getString(R.string.percent_right_answers),
                    gameResult.gameSettings.minPercentRightAnswers
                )*/
                tvPercentRightAnswers.text =
                    String.format(getString(R.string.percent_answers), getPercentOfRightAnswers())

        }
    }

    private fun getPercentOfRightAnswers() = with(args.gameResult){
        if(countOfQuestion == 0){
            0
        }else{
            ((countOfRightAnswers / countOfQuestion.toDouble())*100).toInt()
        }
    }

    private fun getImgResource() : Int {
        val result = if (args.gameResult.winner) {
            R.drawable.smile
        } else {
            R.drawable.nosmile
        }
        return result
    }

    private fun retryGame() {
        findNavController().popBackStack()

    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}