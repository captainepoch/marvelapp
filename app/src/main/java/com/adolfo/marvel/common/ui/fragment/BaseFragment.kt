package com.adolfo.marvel.common.ui.fragment

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.adolfo.marvel.common.navigation.MainActivity

open class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    fun navigateTo(direction: NavDirections) {
        (requireActivity() as MainActivity).navigateTo(direction)
    }
}
