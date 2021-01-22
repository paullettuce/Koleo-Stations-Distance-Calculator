package pl.paullettuce.android_astarium_interview_app.domain.usecase.stations

import androidx.lifecycle.LiveData
import pl.paullettuce.android_astarium_interview_app.domain.model.StationInfo
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsRepository

interface FilterStationsUseCase {
    operator fun invoke(query: String): LiveData<List<StationInfo>>
}

class FilterStationsUseCaseImpl(
    private val stationsRepository: StationsRepository
): FilterStationsUseCase {
    override fun invoke(query: String): LiveData<List<StationInfo>> {
        val sanitizedQuery = sanitizeQuery(query)
        return stationsRepository.filterStations(sanitizedQuery)
    }

    private fun sanitizeQuery(query: String): String {
        return "%$query%"
    }
}
