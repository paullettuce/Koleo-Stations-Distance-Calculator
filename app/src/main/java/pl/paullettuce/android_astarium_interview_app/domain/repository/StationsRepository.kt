package pl.paullettuce.android_astarium_interview_app.domain.repository

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Single
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

interface StationsRepository {
    fun getStations(): LiveData<List<StationInfo>>
    fun filterStations(query: String): LiveData<List<StationInfo>>
    fun downloadAndSaveStations(): Single<ResultWrapper<Boolean>>
    fun getStation(id: Long): Single<StationDataEntity>
}