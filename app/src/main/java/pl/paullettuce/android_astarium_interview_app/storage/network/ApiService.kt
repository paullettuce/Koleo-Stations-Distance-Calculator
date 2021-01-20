package pl.paullettuce.android_astarium_interview_app.storage.network

import io.reactivex.rxjava3.core.Flowable
import pl.paullettuce.android_astarium_interview_app.storage.entity.AutofillHintEntity
import pl.paullettuce.android_astarium_interview_app.storage.entity.StationDataEntity
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("X-KOLEO-Version: 1")
    @GET("main/stations")
    fun getStations(): Flowable<List<StationDataEntity>>

    @Headers("X-KOLEO-Version: 1")
    @GET("main/station_keywords")
    fun getAutofillHints(): Flowable<List<AutofillHintEntity>>
}