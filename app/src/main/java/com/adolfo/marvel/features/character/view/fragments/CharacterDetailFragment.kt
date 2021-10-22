package com.adolfo.marvel.features.character.view.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.core.extensions.observe
import com.adolfo.core.extensions.viewBinding
import com.adolfo.marvel.R
import com.adolfo.marvel.common.ui.fragment.BaseFragment
import com.adolfo.marvel.databinding.FragmentCharacterDetailBinding
import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber

class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {

    private val binding by viewBinding(FragmentCharacterDetailBinding::bind)
    private val viewModel by stateViewModel<CharacterViewModel>()
    private val arguments by navArgs<CharacterDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initView()
    }

    private fun initObservers() {
        with(viewModel) {
            observe(character, ::handleCharacterDetail)
        }
    }

    private fun handleCharacterDetail(characterView: CharacterView?) {
        characterView?.let {
            Timber.d("${it.name}")
        }
    }

    private fun initView() {
        viewModel.getCharacterDetail(arguments.characterDetail?.id)
    }
}
