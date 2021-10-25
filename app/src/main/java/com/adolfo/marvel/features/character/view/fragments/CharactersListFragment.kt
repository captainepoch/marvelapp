package com.adolfo.marvel.features.character.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.core.exception.Failure
import com.adolfo.core.exception.Failure.CustomError
import com.adolfo.core.exception.Failure.NetworkConnection
import com.adolfo.core.exception.Failure.ServerError
import com.adolfo.core.extensions.endlessScrollListener
import com.adolfo.core.extensions.gone
import com.adolfo.core.extensions.viewBinding
import com.adolfo.core.extensions.viewFailureObserve
import com.adolfo.core.extensions.viewObserve
import com.adolfo.core.functional.Event
import com.adolfo.design.common.extensions.actions
import com.adolfo.design.info.InformationView.ACTION.PRIMARY_ACTION
import com.adolfo.marvel.R
import com.adolfo.marvel.common.ui.fragment.BaseFragment
import com.adolfo.marvel.databinding.FragmentCharactersBinding
import com.adolfo.marvel.features.character.view.adapter.CharactersListAdapter
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.stateViewModel

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
            viewObserve(loader, ::showLoader)
            viewObserve(customError, ::handleCustomError)
            viewFailureObserve(failure, ::handleFailure)
        }
    }

    private fun initView() {
        binding.rvCharacters.adapter = charactersAdapter
    }

    private fun initListeners() {
        binding.rvCharacters.endlessScrollListener {
            getCharacters(true)
        }

        charactersAdapter.characterListener = {
            navigateTo(CharactersListFragmentDirections.actionListToDetail(it))
        }
    }

    private fun handleCharacters(charactersView: CharactersView?) {
        charactersView?.let { characters ->
            if (characters.isFullEmtpy) {
                showEmptyState()
            } else if (characters.isPaginationEmpty) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.characters_pagination_empty_title),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                charactersAdapter.submitList(characters.results)
            }
        }
    }

    private fun getCharacters(isPaginated: Boolean = false) {
        viewModel.getCharacters(isPaginated)
    }

    private fun handleCustomError(event: Event<CustomError>?) {
        event?.getContentIfNotHandled()?.let { failure ->
            if (failure.code == CustomError.PAGINATION_ERROR) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.characters_pagination_error),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> {
                showNetworkError()
            }
            is ServerError -> {
                showServerError()
            }
            else -> {
                showUnknownError()
            }
        }
    }

    private fun showNetworkError() {
        binding.errorView.actions(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_error_outline),
            getString(R.string.network_connection_error_title),
            getString(R.string.network_connection_error_desc),
            getString(R.string.button_retry),
            getString(R.string.button_exit)
        )

        binding.errorView.onActionClick = { action ->
            if (action == PRIMARY_ACTION) {
                binding.errorView.gone()

                getCharacters()
            } else {
                requireActivity().finishAndRemoveTask()
            }
        }
    }

    private fun showEmptyState() {
        binding.errorView.actions(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_emoji_people),
            getString(R.string.characters_emtpy_state_title),
            getString(R.string.characters_emtpy_state_desc),
            getString(R.string.button_retry),
            getString(R.string.button_exit),
        )

        binding.errorView.onActionClick = { action ->
            if (action == PRIMARY_ACTION) {
                binding.errorView.gone()

                getCharacters()
            } else {
                requireActivity().finishAndRemoveTask()
            }
        }
    }

    private fun showServerError() {
        binding.errorView.actions(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_error_outline),
            getString(R.string.server_error_title),
            getString(R.string.server_error_desc),
            getString(R.string.button_retry),
            getString(R.string.button_exit)
        )

        binding.errorView.onActionClick = { action ->
            if (action == PRIMARY_ACTION) {
                binding.errorView.gone()

                getCharacters()
            } else {
                requireActivity().finishAndRemoveTask()
            }
        }
    }

    private fun showUnknownError() {
        binding.errorView.actions(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_error_outline),
            getString(R.string.unknown_error_title),
            getString(R.string.unknown_error_desc),
            getString(R.string.button_retry),
            getString(R.string.button_exit)
        )

        binding.errorView.onActionClick = { action ->
            if (action == PRIMARY_ACTION) {
                binding.errorView.gone()

                getCharacters()
            } else {
                requireActivity().finishAndRemoveTask()
            }
        }
    }
}
