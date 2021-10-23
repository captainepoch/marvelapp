package com.adolfo.marvel.features.character.view.fragments

import android.os.Bundle
import android.view.View
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.core.extensions.endlessScrollListener
import com.adolfo.core.extensions.viewBinding
import com.adolfo.core.extensions.viewObserve
import com.adolfo.marvel.R
import com.adolfo.marvel.common.ui.fragment.BaseFragment
import com.adolfo.marvel.databinding.FragmentCharactersBinding
import com.adolfo.marvel.features.character.view.adapter.CharactersListAdapter
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber

class CharactersListFragment : BaseFragment(R.layout.fragment_characters) {

    private val binding by viewBinding(FragmentCharactersBinding::bind)
    private val viewModel by stateViewModel<CharactersViewModel>()

    private val charactersAdapter by lazy { CharactersListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initView()
        initListeners()
    }

    private fun initObservers() {
        with(viewModel) {
            viewObserve(characters, ::handleCharacters)
        }
    }

    private fun initView() {
        charactersAdapter.submitList(mutableListOf())
        binding.rvCharacters.adapter = charactersAdapter
    }

    private fun initListeners() {
        binding.rvCharacters.endlessScrollListener {
            viewModel.getCharacters()
        }

        charactersAdapter.characterListener = {
            navigateTo(CharactersListFragmentDirections.actionListToDetail(it))
        }
    }

    private fun handleCharacters(charactersView: CharactersView?) {
        charactersView?.let { characters ->
            Timber.i(characters.results.toString())
            charactersAdapter.submitList(characters.results)
        }
    }
}
