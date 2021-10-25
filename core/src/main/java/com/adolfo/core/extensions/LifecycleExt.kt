package com.adolfo.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.adolfo.core.exception.Failure

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <T : Any, L : LiveData<T>> Fragment.viewObserve(liveData: L, body: (T?) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer(body))

fun <L : LiveData<Failure>> Fragment.viewFailureObserve(
    liveData: L,
    body: (Failure?) -> Unit
) = liveData.observe(viewLifecycleOwner, Observer(body))
