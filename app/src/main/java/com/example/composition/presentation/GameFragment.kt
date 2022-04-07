package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.presentation.viewmodel.GameViewModel
import com.example.composition.presentation.viewmodel.GameViewModelFactory

class GameFragment : Fragment() {

    private var _bind : FragmentGameBinding? = null
    private val bind : FragmentGameBinding
    get() = _bind ?: throw RuntimeException("FragmentGameBinding == null")
    private val args by navArgs<GameFragmentArgs>()
    private val viewModelFactory by lazy{
        GameViewModelFactory(args.level, requireActivity().application)
    }
    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }
    private val tvOptions by lazy {
                mutableListOf<TextView>().apply {
                    add(bind.tvOpt1)
                    add(bind.tvOpt2)
                    add(bind.tvOpt3)
                    add(bind.tvOpt4)
                    add(bind.tvOpt5)
                    add(bind.tvOpt6)
                }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun observeViewModel() {
        viewModel.gameResult.observe(viewLifecycleOwner){
            goToFinishedFragment(it)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentGameBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.viewModel = viewModel
        bind.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }


    private fun goToFinishedFragment(gameResult: GameResult){
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult))
    }
}