package pl.paullettuce.android_astarium_interview_app.domain.usecase.stations

import io.reactivex.rxjava3.core.Single
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper

interface DownloadAndSaveStationsUseCase {
    operator fun invoke(): Single<ResultWrapper<Boolean>>
}

class DownloadAndSaveStationsUseCaseImpl(
    private val stationsRepository: StationsRepository
) : DownloadAndSaveStationsUseCase {
    override fun invoke() = stationsRepository.downloadAndSaveStations()
}