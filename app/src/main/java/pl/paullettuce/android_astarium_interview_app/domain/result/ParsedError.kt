package pl.paullettuce.android_astarium_interview_app.domain.result


sealed class ParsedError {
    data class UnknownException(val throwable: Throwable) : ParsedError()
    data class UnknownHttpCode(val httpErrorCode: Int) : ParsedError()
    data class ErrorSavingDataToDb(val throwable: Throwable) : ParsedError()
    data object MissingHeader : ParsedError()
    data object NoConnection : ParsedError()
}
