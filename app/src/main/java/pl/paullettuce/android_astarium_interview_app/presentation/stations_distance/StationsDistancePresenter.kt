package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import pl.paullettuce.android_astarium_interview_app.domain.extensions.logd
import pl.paullettuce.android_astarium_interview_app.domain.result.ParsedError
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.GetStationsUseCase
import pl.paullettuce.android_astarium_interview_app.storage.model.StationInfo
import javax.inject.Inject

class StationsDistancePresenter
@Inject constructor(
    private val view: StationsDistanceContract.View,
    private val getStationsUseCase: GetStationsUseCase
) : StationsDistanceContract.Presenter {
    private val compositeDisposable = CompositeDisposable()

    override val stationsLiveData: LiveData<List<StationInfo>>
        get() = _stationsLiveData
    private val _stationsLiveData = MutableLiveData<List<StationInfo>>()

    override fun initialize() {
        fetchStations()
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }

    private fun fetchStations() {
        getStationsUseCase()
            .subscribeBy { handleStationsResult(it) }
            .addTo(compositeDisposable)
    }

    private fun handleStationsResult(result: ResultWrapper<List<StationInfo>>) {
        when (result) {
            is ResultWrapper.Success -> _stationsLiveData.postValue(result.data)
            is ResultWrapper.Failure -> handleError(result.error)
            is ResultWrapper.Loading -> view.showLoading()
        }
    }

    private fun handleError(parsedError: ParsedError) {
        when (parsedError) {
            is ParsedError.UnknownException -> logd("Unknown exception: ${parsedError.throwable}")
            is ParsedError.UnknownHttpCode -> logd("Unhandled error code: ${parsedError.httpErrorCode}")
            ParsedError.MissingHeader -> view.showErrorMessage("Request is missing header")
            ParsedError.NoConnection -> view.showNoConnectionError()
        }
    }
}