package com.adolfo.marvel.features.character.view.fragments

import android.os.Bundle
import android.view.View
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.core.extensions.observe
import com.adolfo.core.extensions.viewBinding
import com.adolfo.core.ui.fragment.BaseFragment
import com.adolfo.marvel.R
import com.adolfo.marvel.databinding.FragmentCharactersBinding
import com.adolfo.marvel.features.character.view.adapter.CharactersListAdapter
import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class CharactersListFragment : BaseFragment(R.layout.fragment_characters) {

    private val binding by viewBinding(FragmentCharactersBinding::bind)
    private val viewModel by stateViewModel<CharacterViewModel>()

    private val charactersAdapter by lazy { CharactersListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            observe(characters, ::handleCharacters)
        }

        initView()
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.rvCharacters.adapter = null
    }

    private fun initView() {
        binding.rvCharacters.adapter = charactersAdapter
    }

    private fun initListeners() {

    }

    private fun handleCharacters(charactersView: CharactersView?) {
        charactersView?.let {
            //charactersAdapter.items = it.results
            charactersAdapter.submitList(it.results)
        }
    }
}
