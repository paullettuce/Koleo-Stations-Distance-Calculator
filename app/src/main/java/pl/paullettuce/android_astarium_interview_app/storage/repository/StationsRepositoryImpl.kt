package pl.paullettuce.android_astarium_interview_app.storage.repository

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.paullettuce.android_astarium_interview_app.domain.extensions.mapNotNull
import pl.paullettuce.android_astarium_interview_app.domain.mappers.StationEntityToStationInfoListMapper
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
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

    override fun filterStations(query: String): LiveData<List<StationInfo>> {
        return stationDataDao.filterStations(query)
            .mapNotNull {
                stationInfoListMapper.map(it)
            }
    }

    override fun getStation(id: Long): Single<StationDataEntity> {
        return stationDataDao.getStation(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun downloadStations(): Flowable<List<StationDataEntity>> {
        return apiService.getStations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveStations(stations: List<StationDataEntity>): Completable {
        return stationDataDao.insert(*stations.toTypedArray())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
