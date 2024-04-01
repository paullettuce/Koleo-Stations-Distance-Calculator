package pl.paullettuce.android_astarium_interview_app.domain.usecase.stations

import io.reactivex.rxjava3.core.Single
import pl.paullettuce.android_astarium_interview_app.domain.distance.DistanceCalculator
import pl.paullettuce.android_astarium_interview_app.domain.mappers.StationEntityToPointMapper
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository

interface CalculateDistanceUseCase {
    operator fun invoke(station1: StationInfo, station2: StationInfo): Single<Int>
}

class CalculateDistanceUseCaseImpl(
    private val stationsRepository: StationsRepository,
    private val mapper: StationEntityToPointMapper
) : CalculateDistanceUseCase {
    override fun invoke(station1: StationInfo, station2: StationInfo): Single<Int> {
        return Single.zip(
            stationsRepository.getStation(station1.id).map { mapper.map(it) },
            stationsRepository.getStation(station2.id).map { mapper.map(it) }
        ) { t1, t2 -> Pair(t1, t2) }.flatMap {
            Single.just(DistanceCalculator.betweenPoints(it.first, it.second).toInt())
        }
    }
}