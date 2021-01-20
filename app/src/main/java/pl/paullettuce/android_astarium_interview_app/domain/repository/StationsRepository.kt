package pl.paullettuce.android_astarium_interview_app.domain.repository

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

interface StationsRepository {
    fun getStations(): LiveData<List<StationInfo>>
    fun downloadStations(): Flowable<ResultWrapper<List<StationDataEntity>>>
    fun saveStations(stations: List<StationDataEntity>): Completable
}