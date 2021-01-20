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
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.CalculateDistanceUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.GetStationsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize.SynchronizeStationsUseCase
import javax.inject.Inject

class StationsDistancePresenter
@Inject constructor(
    private val view: StationsDistanceContract.View,
    private val getStationsUseCase: GetStationsUseCase,
    private val synchronizeStationsUseCase: SynchronizeStationsUseCase,
    private val calculateDistanceUseCase: CalculateDistanceUseCase
) : StationsDistanceContract.Presenter, StateControl {
    private val compositeDisposable = CompositeDisposable()

    private var stationsPickingState: StationsPickingState = NoStationSelected()

    override fun initialize() {
        fetchStations()
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }

    override fun stationsInfoObservableData(): LiveData<List<StationInfo>> {
        return getStationsUseCase()
    }

    override fun onStationInfoListItemClick(item: StationInfo) {
        stationsPickingState.onStationInfoClick(item, this)
    }

    override fun changeStateTo(newState: StationsPickingState) {
        stationsPickingState = newState
        when (newState) {
            is NoStationSelected -> {
                view.noStationsSelected()
            }
            is OneStationSelected -> {
                view.oneStationSelected(newState.selectedStation)
            }
            is TwoStationsSelected -> {
                view.twoStationsSelected(newState.station1, newState.station2)
                calculateDistanceUseCase(newState.station1, newState.station2)
                    .subscribeBy { view.showDistance(it) }
                    .addTo(compositeDisposable)
            }
        }
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
            is ParsedError.ErrorSavingDataToDb -> loge("There have been some trouble saving data.\n${parsedError.throwable}")
            ParsedError.MissingHeader -> loge("Request is missing header")
            ParsedError.NoConnection -> view.showNoConnectionError()
        }
    }
}