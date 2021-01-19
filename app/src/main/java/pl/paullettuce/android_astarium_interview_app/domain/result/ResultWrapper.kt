package pl.paullettuce.android_astarium_interview_app.domain.result

sealed class ResultWrapper<out T> {
    class Success<out R>(val data: R) : ResultWrapper<R>()
    class Failure(val error: ParsedError): ResultWrapper<Nothing>()
    object Loading: ResultWrapper<Nothing>()

    fun isSuccess() = this is Success
    fun isFailure() = this is Failure
    fun isLoading() = this is Loading

    companion object {
        fun <R>success(data: R): ResultWrapper<R> = Success(data)
        fun failure(error: ParsedError): ResultWrapper<Nothing> = Failure(error)
        fun loading(): ResultWrapper<Nothing> = Loading
    }
}

sealed class ResultOf<out T> {
    data class Success<out R>(val value: R): ResultOf<R>()
    data class Failure(
        val message: String?,
        val throwable: Throwable?
    ): ResultOf<Nothing>()
}