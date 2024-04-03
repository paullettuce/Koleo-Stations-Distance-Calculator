package pl.paullettuce.android_astarium_interview_app.domain.repository

import io.reactivex.rxjava3.core.Single
import pl.paullettuce.android_astarium_interview_app.domain.result.ResultWrapper

interface StationsKeywordsRepository {

    fun downloadAndSaveStationsKeywords(): Single<ResultWrapper<Boolean>>
}