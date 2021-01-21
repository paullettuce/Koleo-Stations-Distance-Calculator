package pl.paullettuce.android_astarium_interview_app.domain.usecase.stations

import io.reactivex.rxjava3.core.Flowable
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity

interface DownloadStationsUseCase {
    operator fun invoke(): Flowable<List<StationDataEntity>>
}

class DownloadStationsUseCaseImpl(
    private val stationsRepository: StationsRepository
): DownloadStationsUseCase {
    override fun invoke() = stationsRepository.downloadStations()
}