package com.adolfo.marvel.common.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.adolfo.core.exception.Failure
import com.adolfo.marvel.common.platform.AppConstants

open class BaseViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val loaderLiveData =
        savedStateHandle.getLiveData<Boolean>(AppConstants.LiveData.LOADER_VM)
    val loader: LiveData<Boolean> get() = loaderLiveData

    private val failureLiveData =
        savedStateHandle.getLiveData<Failure>(AppConstants.LiveData.FAILURE_VM)
    val failure: LiveData<Failure> get() = failureLiveData

    protected fun showLoader(show: Boolean?) {
        loaderLiveData.value = show
    }

    protected fun handleFailure(failure: Failure?) {
        failureLiveData.value = failure
        showLoader(false)
    }
}
