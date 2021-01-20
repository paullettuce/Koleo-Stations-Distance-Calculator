package pl.paullettuce.android_astarium_interview_app.storage.repository

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.paullettuce.android_astarium_interview_app.domain.extensions.logd
import pl.paullettuce.android_astarium_interview_app.domain.extensions.loge
import pl.paullettuce.android_astarium_interview_app.domain.mappers.StationEntityToStationInfoListMapper
import pl.paullettuce.android_astarium_interview_app.domain.mappers.mapNotNull
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ErrorParser
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.storage.dao.StationDataDao
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity
import pl.paullettuce.android_astarium_interview_app.storage.network.ApiService

class StationsRepositoryImpl(
    private val apiService: ApiService,
    private val stationDataDao: StationDataDao,
    private val stationInfoListMapper: StationEntityToStationInfoListMapper
) : StationsRepository {

    override fun getStations(): LiveData<List<StationInfo>> {
        return stationDataDao.getStationsData()
            .mapNotNull {
                stationInfoListMapper.map(it)
            }
    }

    override fun downloadStations(): Flowable<ResultWrapper<List<StationDataEntity>>> {
        return apiService.getStations()
            .doOnSubscribe { Flowable.just(ResultWrapper.loading()) }
            .map {
                logd("Stations downloaded successfully.\n", it)
                ResultWrapper.success(it)
            }
            .onErrorReturn {
                loge(it)
                ResultWrapper.failure(ErrorParser.parseError(it))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveStations(stations: List<StationDataEntity>): Completable {
        return stationDataDao.insert(*stations.toTypedArray())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
