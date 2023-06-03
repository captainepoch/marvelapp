package com.adolfo.core.exception

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Deprecated("Old class for errors")
sealed class Failure {

    @Parcelize
    data class ServerError(val code: Int) : Failure(), Parcelable {
        companion object CustomServerCodes {
            const val SSL_HANDSHAKE_EXCEPTION = -1
        }
    }

    @Parcelize
    data class Throwable(val throwable: kotlin.Throwable?) : Failure(), Parcelable

    @Parcelize
    data class CustomError(val code: Int, val msg: String? = null) : Failure(), Parcelable {
        companion object CharactersCodes {
            const val PAGINATION_ERROR = 1
        }
    }

    object NetworkConnection : Failure()
}
