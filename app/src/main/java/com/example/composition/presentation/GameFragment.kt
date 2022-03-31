package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level
    private lateinit var gameSettings: GameSettings
    private var _bind : FragmentGameBinding? = null
    private val bind : FragmentGameBinding
    get() = _bind ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        setGameSettings(level)
        goToFinishedFragment(GameResult(true, 10,10, gameSettings))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentGameBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }

    private fun parseArgs(){
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    private fun setGameSettings(level : Level){
        gameSettings = when (level){
            Level.TEST -> GameSettings(10, 6, 10, 10)
            Level.EASY -> GameSettings(20, 6, 10, 10)
            Level.NORMAL -> GameSettings(30, 6, 10, 10)
            Level.HARD -> GameSettings(40, 6, 10, 10)
        }
    }

    private fun goToFinishedFragment(gameResult: GameResult){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object{

        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(level : Level) : GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }
}