package com.adolfo.marvel.features.character.view.fragments

import android.os.Bundle
import android.view.View
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.core.extensions.observe
import com.adolfo.core.extensions.viewBinding
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
            observe(characters, ::handleCharacters)
        }
    }

    private fun initView() {
        binding.rvCharacters.adapter = charactersAdapter
    }

    private fun initListeners() {
        charactersAdapter.characterListener = {
            Timber.d("${it.name}")

            navigateTo(CharactersListFragmentDirections.actionListToDetail(it))
        }
    }

    private fun handleCharacters(charactersView: CharactersView?) {
        charactersView?.let {
            val actualList = charactersAdapter.currentList.toMutableList()
            actualList.addAll(it.results)
            charactersAdapter.submitList(actualList)

            Timber.d("Updated characters")
        }
    }
}
