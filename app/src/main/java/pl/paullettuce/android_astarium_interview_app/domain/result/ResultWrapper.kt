package pl.paullettuce.android_astarium_interview_app.domain.result

sealed class ResultWrapper<out T> {
    class Success<out R>(val data: R) : ResultWrapper<R>()
    class Failure(val error: ParsedError): ResultWrapper<Nothing>()

    companion object {
        fun <R>success(data: R): ResultWrapper<R> = Success(data)
        fun failure(error: ParsedError): ResultWrapper<Nothing> = Failure(error)
    }
}