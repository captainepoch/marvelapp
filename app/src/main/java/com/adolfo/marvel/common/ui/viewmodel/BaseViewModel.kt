package com.adolfo.marvel.common.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adolfo.core.exception.Failure

@Deprecated("Not for Compose.")
open class BaseViewModel : ViewModel() {

    private val loaderLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loader: LiveData<Boolean> get() = loaderLiveData

    private val failureLiveData: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> get() = failureLiveData

    protected fun showLoader(show: Boolean?) {
        loaderLiveData.value = show
    }

    protected fun handleFailure(failure: Failure?) {
        failureLiveData.value = failure
        showLoader(false)
    }
}
