package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
        bind.gameResult = args.gameResult
        bind.returnButton.setOnClickListener {
            retryGame()
        }
    }

    private fun retryGame() {
        findNavController().popBackStack()

    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}