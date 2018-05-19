package co.laith.clearscoredount.error

class CSException(val errorType: ErrorType, val messageResId: Int) : Throwable() {

    enum class ErrorType {
        SERVER, UNKNOWN
    }
}
