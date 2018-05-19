package co.laith.clearscoredount.error

import org.junit.Assert.*
import org.junit.Test
import java.net.UnknownHostException

class ErrorMapperTest {

    @Test
    fun itShouldReturnUnKnownError() {
        val unknownError = ErrorMapper.map(Exception("Unknown error!"))
        assertTrue(unknownError.errorType == CSException.ErrorType.UNKNOWN)
    }

    @Test
    fun itShouldReturnServerError() {
        val unknownError = ErrorMapper.map(UnknownHostException("Host exception!"))
        assertTrue(unknownError.errorType == CSException.ErrorType.SERVER)
    }
}