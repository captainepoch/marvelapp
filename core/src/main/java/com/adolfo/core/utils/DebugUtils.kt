package com.adolfo.core.utils

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.StrictMode
import com.adolfo.core.BuildConfig
import timber.log.Timber

object DebugUtils {

    fun isDebug() = BuildConfig.DEBUG

    fun enableStrictMode() {
        if (isDebug()) {
            val threadPolicyBuilder =
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()

            if (VERSION.SDK_INT >= VERSION_CODES.O) {
                threadPolicyBuilder.detectUnbufferedIo()
            }

            StrictMode.setThreadPolicy(threadPolicyBuilder.build())
            Timber.tag("StrictMode").i("setThreadPolicy")

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            Timber.tag("StrictMode").i("setVmPolicy")
        }
    }
}
