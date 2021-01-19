package pl.paullettuce.android_astarium_interview_app.storage.repository

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.paullettuce.android_astarium_interview_app.domain.extensions.logd
import pl.paullettuce.android_astarium_interview_app.domain.extensions.loge
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ErrorParser
import pl.paullettuce.android_astarium_interview_app.domain.result.ParsedError
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.storage.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.storage.network.ApiService

class StationsRepositoryImpl(
    private val apiService: ApiService
) : StationsRepository {
    override fun getStations(): Flowable<ResultWrapper<List<StationInfo>>> {
        return apiService.getStations()
            .doOnSubscribe { Flowable.just(ResultWrapper.loading()) }
            .map {
                logd("Stations downloaded successfully.\n")
                ResultWrapper.success(it)
            }
            .onErrorReturn {
                loge(it)
                ResultWrapper.failure(ErrorParser.parseError(it))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
