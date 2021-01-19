package pl.paullettuce.android_astarium_interview_app.storage.network

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import pl.paullettuce.android_astarium_interview_app.storage.model.AutofillHint
import pl.paullettuce.android_astarium_interview_app.storage.model.StationInfo
import retrofit2.http.GET

interface ApiService {

    @GET("main/stations")
    fun getStations(): Flowable<List<StationInfo>>

    @GET("main/station_keywords")
    fun getAutofillHints(): Flowable<List<AutofillHint>>
}