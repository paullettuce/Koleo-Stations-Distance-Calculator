package pl.paullettuce.android_astarium_interview_app.domain.usecase.stations_keywords

import io.reactivex.rxjava3.core.Single
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsKeywordsRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import javax.inject.Inject

interface DownloadAndSaveStationsKeywordsUseCase {
    operator fun invoke(): Single<ResultWrapper<Boolean>>
}

class DownloadAndSaveStationsKeywordsUseCaseImpl @Inject constructor(
    private val stationsKeywordsRepository: StationsKeywordsRepository
) : DownloadAndSaveStationsKeywordsUseCase {
    override fun invoke() = stationsKeywordsRepository.downloadAndSaveStationsKeywords()
}