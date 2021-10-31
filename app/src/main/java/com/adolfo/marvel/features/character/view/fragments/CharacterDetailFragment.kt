package com.adolfo.marvel.features.character.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.core.exception.Failure
import com.adolfo.core.exception.Failure.NetworkConnection
import com.adolfo.core.exception.Failure.ServerError
import com.adolfo.core.extensions.gone
import com.adolfo.core.extensions.isEmptyOrBlank
import com.adolfo.core.extensions.loadFromUrl
import com.adolfo.core.extensions.viewFailureObserve
import com.adolfo.core.extensions.viewObserve
import com.adolfo.design.common.extensions.actions
import com.adolfo.design.info.InformationView.ACTION.PRIMARY_ACTION
import com.adolfo.marvel.R
import com.adolfo.marvel.common.extensions.viewBinding
import com.adolfo.marvel.common.ui.fragment.BaseFragment
import com.adolfo.marvel.databinding.FragmentCharacterDetailBinding
import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {

    private val binding by viewBinding(FragmentCharacterDetailBinding::bind)
    private val viewModel by viewModel<CharacterViewModel>()
    private val arguments by navArgs<CharacterDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initView()
        initListeners()
    }

    private fun initObservers() {
        with(viewModel) {
            viewObserve(character, ::handleCharacterDetail)
            viewObserve(loader, ::showLoader)
            viewFailureObserve(failure, ::handleFailure)
        }
    }

    private fun handleCharacterDetail(characterView: CharacterView?) {
        characterView?.let { character ->
            binding.ivDetailAvatar.loadFromUrl(character.thumbnail)

            getMainActivity().setToolbarTitle(character.name)

            binding.tvDescription.text = if (character.description.isEmptyOrBlank()) {
                getString(R.string.character_detail_no_description)
            } else {
                character.description
            }
        }
    }

    private fun initView() {
        getCharacter()
    }

    private fun getCharacter() {
        viewModel.getCharacterDetail(arguments.characterDetail?.id)
    }

    private fun initListeners() {
        binding.errorView.onActionClick = { action ->
            binding.errorView.gone()

            if (action == PRIMARY_ACTION) {
                getCharacter()
            } else {
                navigateBack()
            }
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> {
                binding.errorView.actions(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_error_outline),
                    getString(R.string.network_connection_error_title),
                    getString(R.string.network_connection_error_desc),
                    getString(R.string.button_retry),
                    getString(R.string.button_goback)
                )

                binding.errorView.onActionClick = { action ->
                    if (action == PRIMARY_ACTION) {
                        binding.errorView.gone()

                        getCharacter()
                    } else {
                        navigateBack()
                    }
                }
            }
            is ServerError -> {
                binding.errorView.actions(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_error_outline),
                    getString(R.string.server_error_title),
                    getString(R.string.server_error_desc),
                    getString(R.string.button_retry),
                    getString(R.string.button_goback)
                )

                binding.errorView.onActionClick = { action ->
                    if (action == PRIMARY_ACTION) {
                        binding.errorView.gone()

                        getCharacter()
                    } else {
                        navigateBack()
                    }
                }
            }
            else -> {
                binding.errorView.actions(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_error_outline),
                    getString(R.string.unknown_error_title),
                    getString(R.string.unknown_error_desc),
                    getString(R.string.button_retry),
                    getString(R.string.button_goback)
                )

                binding.errorView.onActionClick = { action ->
                    if (action == PRIMARY_ACTION) {
                        binding.errorView.gone()

                        getCharacter()
                    } else {
                        navigateBack()
                    }
                }
            }
        }
    }
}
