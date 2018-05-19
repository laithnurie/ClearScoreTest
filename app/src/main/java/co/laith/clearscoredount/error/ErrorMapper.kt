package co.laith.clearscoredount.error

import java.net.UnknownHostException

import co.laith.clearscoredount.R
import co.laith.clearscoredount.error.CSException.ErrorType

object ErrorMapper {

    fun map(throwable: Throwable): CSException {

        var errorType = ErrorType.UNKNOWN
        var messageResourceId = R.string.error_unknown

        // error mapping
        if (throwable is UnknownHostException) {
            errorType = ErrorType.SERVER
            messageResourceId = R.string.error_network
        }

        return CSException(errorType, messageResourceId)
    }
}
