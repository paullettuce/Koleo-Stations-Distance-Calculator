package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import pl.paullettuce.android_astarium_interview_app.R
import pl.paullettuce.android_astarium_interview_app.domain.extensions.loge
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.domain.result.ParsedError
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.GetStationsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize.SynchronizeStationsUseCase
import javax.inject.Inject

class StationsDistancePresenter
@Inject constructor(
    private val view: StationsDistanceContract.View,
    private val getStationsUseCase: GetStationsUseCase,
    private val synchronizeStationsUseCase: SynchronizeStationsUseCase
) : StationsDistanceContract.Presenter {
    private val compositeDisposable = CompositeDisposable()

    override fun initialize() {
        fetchStations()
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }

    override fun stationsInfoObservableData(): LiveData<List<StationInfo>> {
        return getStationsUseCase()
    }

    private fun fetchStations() {
        synchronizeStationsUseCase()
            .subscribeBy { handleStationsResult(it) }
            .addTo(compositeDisposable)
    }

    private fun handleStationsResult(result: ResultWrapper<Boolean>) {
        when (result) {
            is ResultWrapper.Success -> {
                if (result.data) // successfully synchronized. False would mean up-to-date
                    view.showMessage(R.string.station_list_synchronized)
            }
            is ResultWrapper.Failure -> handleError(result.error)
            is ResultWrapper.Loading -> view.showLoading()
        }
    }

    private fun handleError(parsedError: ParsedError) {
        when (parsedError) {
            is ParsedError.UnknownException -> loge(parsedError.throwable)
            is ParsedError.UnknownHttpCode -> loge("Unhandled error code: ${parsedError.httpErrorCode}")
            ParsedError.MissingHeader -> view.showMessage("Request is missing header")
            ParsedError.NoConnection -> view.showNoConnectionError()
            ParsedError.ErrorSavingDataToDb -> view.showMessage("There have been some trouble saving data")
        }
    }
}