package pl.paullettuce.android_astarium_interview_app.domain.usecase

import androidx.lifecycle.LiveData
import pl.paullettuce.android_astarium_interview_app.storage.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository

interface GetStationsUseCase {
    operator fun invoke(): LiveData<List<StationInfo>>
}

class GetStationsUseCaseImpl(
    private val stationsRepository: StationsRepository
): GetStationsUseCase {
    override fun invoke() = stationsRepository.getStations()
}