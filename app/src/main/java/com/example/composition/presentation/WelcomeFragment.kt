package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _bind: FragmentWelcomeBinding? = null
    private val bind: FragmentWelcomeBinding
        get() = _bind ?: throw RuntimeException("FragmentWelcomeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentWelcomeBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.bNext.setOnClickListener {
            goToChooseFragment()
        }
    }


    private fun goToChooseFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, ChooseLevelFragment.newInstance())
            .addToBackStack(ChooseLevelFragment.NAME).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}