package pl.paullettuce.android_astarium_interview_app.domain.repository

import androidx.lifecycle.LiveData
import pl.paullettuce.android_astarium_interview_app.storage.model.StationInfo

interface StationsRepository {
    fun getStations(): LiveData<List<StationInfo>>
}