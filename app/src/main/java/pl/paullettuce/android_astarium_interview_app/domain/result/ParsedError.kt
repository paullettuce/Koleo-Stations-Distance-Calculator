package pl.paullettuce.android_astarium_interview_app.domain.result

import retrofit2.HttpException
import java.net.UnknownHostException


sealed class ParsedError {
    class UnknownException(val throwable: Throwable): ParsedError()
    class UnknownHttpCode(val httpErrorCode: Int): ParsedError()
    object MissingHeader: ParsedError()
    object NoConnection: ParsedError()
}

object ErrorParser {
    fun parseError(throwable: Throwable): ParsedError {
        return when(throwable) {
            is HttpException -> httpCodeToSpecificError(throwable.code())
            is UnknownHostException -> ParsedError.NoConnection
            else -> ParsedError.UnknownException(throwable)
        }
    }

    private fun httpCodeToSpecificError(code: Int): ParsedError {
        return when(code) {
            426 -> ParsedError.MissingHeader
            else -> ParsedError.UnknownHttpCode(code)
        }
    }
}