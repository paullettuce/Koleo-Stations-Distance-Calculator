package pl.paullettuce.android_astarium_interview_app.domain.usecase.stations

import io.reactivex.rxjava3.core.Flowable
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.storage.model.StationInfo

interface GetStationsUseCase {
    operator fun invoke(): Flowable<ResultWrapper<List<StationInfo>>>
}

class GetStationsUseCaseImpl(
    private val stationsRepository: StationsRepository
): GetStationsUseCase {
    override fun invoke() = stationsRepository.getStations()
}