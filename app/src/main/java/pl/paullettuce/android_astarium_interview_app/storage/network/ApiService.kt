package pl.paullettuce.android_astarium_interview_app.storage.network

import io.reactivex.rxjava3.core.Single
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationKeywordEntity
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("X-KOLEO-Version: 1")
    @GET("main/stations")
    fun getStations(): Single<List<StationDataEntity>>

    @Headers("X-KOLEO-Version: 1")
    @GET("main/station_keywords")
    fun getStationsKeywords(): Single<List<StationKeywordEntity>>
}