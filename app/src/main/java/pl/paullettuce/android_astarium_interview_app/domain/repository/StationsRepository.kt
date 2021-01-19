package pl.paullettuce.android_astarium_interview_app.domain.repository

import io.reactivex.rxjava3.core.Flowable
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.storage.model.StationInfo

interface StationsRepository {
    fun getStations(): Flowable<ResultWrapper<List<StationInfo>>>
}