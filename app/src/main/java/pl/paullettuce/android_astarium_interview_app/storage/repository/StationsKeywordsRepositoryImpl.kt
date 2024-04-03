package pl.paullettuce.android_astarium_interview_app.storage.repository

import io.reactivex.rxjava3.core.Single
import pl.paullettuce.android_astarium_interview_app.domain.repository.StationsKeywordsRepository
import pl.paullettuce.android_astarium_interview_app.domain.result.ParsedError
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper
import pl.paullettuce.android_astarium_interview_app.storage.dao.StationKeywordsDao
import pl.paullettuce.android_astarium_interview_app.storage.network.ApiService
import javax.inject.Inject

class StationsKeywordsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val stationKeywordsDao: StationKeywordsDao
) : StationsKeywordsRepository {

    override fun downloadAndSaveStationsKeywords(): Single<ResultWrapper<Boolean>> {
        return apiService.getStationsKeywords()
            .flatMap { keywords ->
                stationKeywordsDao.insert(keywords)
                    .andThen(Single.just(ResultWrapper.success(true)))
                    .onErrorReturn { ResultWrapper.failure(ParsedError.ErrorSavingDataToDb(it)) }
            }
    }
}