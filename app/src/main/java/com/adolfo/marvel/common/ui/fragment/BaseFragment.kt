package com.adolfo.marvel.common.ui.fragment

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.adolfo.marvel.common.navigation.MainActivity

open class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    fun getMainActivity(): MainActivity {
        return (requireActivity() as MainActivity)
    }

    fun navigateTo(direction: NavDirections) {
        getMainActivity().navigateTo(direction)
    }

    fun showLoader(show: Boolean?) {
        getMainActivity().showLoader(show ?: false)
    }
}
