package com.adolfo.marvel.common.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.adolfo.marvel.common.platform.AppConstants

open class BaseViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val loaderLiveData =
        savedStateHandle.getLiveData<Boolean>(AppConstants.LiveData.LOADER_VM)
    val loader: LiveData<Boolean> get() = loaderLiveData

    protected fun showLoader(show: Boolean?) {
        loaderLiveData.value = show
    }
}
