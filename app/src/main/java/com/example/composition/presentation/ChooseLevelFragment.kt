package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.FragmentChooseLevelBinding
import com.example.composition.domain.entity.Level
import java.io.Serializable

class ChooseLevelFragment : Fragment() {

    private var _bind: FragmentChooseLevelBinding? = null
    private val bind: FragmentChooseLevelBinding
        get() = _bind ?: throw RuntimeException("FragmentChooseLevelBinding == null")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(bind){
            buttonLevelTest.setOnClickListener {
                goToGameFragment(Level.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                goToGameFragment(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                goToGameFragment(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                goToGameFragment(Level.HARD)
            }
        }
    }

    private fun goToGameFragment(level : Level) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }

    companion object {

        const val NAME = "ChooseLevelFragment"

        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }
}