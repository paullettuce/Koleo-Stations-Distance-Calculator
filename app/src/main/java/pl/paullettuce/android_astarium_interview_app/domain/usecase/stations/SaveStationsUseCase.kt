package pl.paullettuce.android_astarium_interview_app.domain.usecase.stations

import io.reactivex.rxjava3.core.Flowable
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ParsedError
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

interface SaveStationsUseCase {
    operator fun invoke(stations: List<StationDataEntity>): Flowable<ResultWrapper<Boolean>>
}

class SaveStationsUseCaseImpl(
    private val stationsRepository: StationsRepository
) : SaveStationsUseCase {
    override fun invoke(stations: List<StationDataEntity>): Flowable<ResultWrapper<Boolean>> {
        return stationsRepository.saveStations(stations)
            .andThen(Flowable.just(ResultWrapper.success(true)))
            .onErrorReturn { ResultWrapper.failure(ParsedError.ErrorSavingDataToDb) }
    }
}