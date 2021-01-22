package pl.paullettuce.android_astarium_interview_app.presentation.stations_distance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import pl.paullettuce.android_astarium_interview_app.R
import pl.paullettuce.android_astarium_interview_app.domain.extensions.loge
import pl.paullettuce.android_astarium_interview_app.domain.extensions.switchMap
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.domain.result.ParsedError
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.CalculateDistanceUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.stations.FilterStationsUseCase
import pl.paullettuce.android_astarium_interview_app.domain.usecase.synchronize.SynchronizeStationsUseCase
import javax.inject.Inject

class StationsDistancePresenter
@Inject constructor(
    private val view: StationsDistanceContract.View,
    private val filterStationsUseCase: FilterStationsUseCase,
    private val synchronizeStationsUseCase: SynchronizeStationsUseCase,
    private val calculateDistanceUseCase: CalculateDistanceUseCase
) : StationsDistanceContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private val _searchQuery = MutableLiveData<String>("")
    override val filteredStationsLiveData: LiveData<List<StationInfo>>
        get() = _searchQuery.switchMap { query ->
            filterStationsUseCase(query)
        }

    override fun initialize() {
        synchronizeData()
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }

    override fun calculateDistance(item1: StationInfo, item2: StationInfo) {
        calculateDistanceUseCase(item1, item2)
            .subscribeBy { view.showDistance(it) }
            .addTo(compositeDisposable)
    }

    override fun filterStationsByQuery(query: String) {
        _searchQuery.postValue(query)
    }

    override fun synchronizeData() {
        synchronizeStationsUseCase()
            .doOnSubscribe { view.showLoading(true) }
            .subscribeBy {
                view.showLoading(false)
                handleStationsResult(it)
            }
            .addTo(compositeDisposable)
    }

    private fun handleStationsResult(result: ResultWrapper<Boolean>) {
        when (result) {
            is ResultWrapper.Success -> {
                if (result.data) { // successfully synchronized. False would mean up-to-date
                    view.showMessage(R.string.station_list_synchronized)
                }
            }
            is ResultWrapper.Failure -> {
                handleError(result.error)
            }
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